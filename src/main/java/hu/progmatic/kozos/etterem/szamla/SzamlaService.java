package hu.progmatic.kozos.etterem.szamla;

import hu.progmatic.kozos.etterem.leltar.EtteremTermekDto;
import hu.progmatic.kozos.etterem.rendeles.Rendeles;
import hu.progmatic.kozos.etterem.rendeles.RendelesDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import hu.progmatic.kozos.etterem.asztal.Asztal;
import hu.progmatic.kozos.etterem.asztal.AsztalService;

import java.util.List;


@Service
@Transactional
public class SzamlaService {
  @Autowired
  private SzamlaRepository szamlaRepository;
  @Autowired
  private AsztalService asztalService;
  @Autowired
  private SzamlaTetelRepository szamlaTetelRepository;

  public Szamla create(Szamla szamla) {
    return szamlaRepository.save(szamla);
  }

  public void delete(Integer id) {
    szamlaRepository.deleteById(id);
  }

  public void createSzamlaForAsztal(Integer asztalId) {
    Asztal asztal = asztalService.getById(asztalId);
    if (asztal.getSzamla() != null) {
      szamlaRepository.deleteById(asztal.getSzamla().getId());
    }
    Szamla szamla = Szamla.builder()
        .asztal(asztal)
        .build();
    setTetelek(szamla);
    asztal.setSzamla(szamla);
    szamlaRepository.save(szamla);
  }

  private void setTetelek(Szamla szamla) {
    szamla.setTetelek(szamla.getAsztal().getRendelesek().stream()
        .map(rendeles -> SzamlaTetel.builder()
            .rendeles(rendeles)
            .fizetettMennyiseg(0)
            .nemFizetettMennyiseg(rendeles.getMennyiseg())
            .szamla(szamla)
            .build())
        .toList());
  }

  public Szamla findSzamlaByAsztalId(Integer asztalId) {
    return szamlaRepository.findByAsztal_Id(asztalId);
  }

  public SzamlaDto szamlaDtoBuilder(Szamla szamla) {
    return SzamlaDto.builder()
        .id(szamla.getId())
        .asztalId(szamla.getAsztal().getId())
        .vegosszeg(getVegosszeg(szamla))
        .fizetettVegosszeg(getFizetettVegosszeg(szamla))
        .tetelek(getTetelDtoList(szamla.getTetelek()))
        .split(szamla.isSplit())
        .build();
  }

  private List<SzamlaTetelDto> getTetelDtoList(List<SzamlaTetel> tetelek) {
    return tetelek.stream()
        .map(tetel -> SzamlaTetelDto.builder()
            .id(tetel.getId())
            .rendelesDto(RendelesDto.builder()
                .id(tetel.getRendeles().getId())
                .etteremTermekDto(EtteremTermekDto.builder()
                    .id(tetel.getRendeles().getEtteremTermek().getId())
                    .nev(tetel.getRendeles().getEtteremTermek().getNev())
                    .ar(tetel.getRendeles().getEtteremTermek().getAr())
                    .build())
                .mennyiseg(tetel.getRendeles().getMennyiseg())
                .build())
            .fizetettMennyiseg(tetel.getFizetettMennyiseg())
            .nemFizetettMennyiseg(tetel.getNemFizetettMennyiseg())
            .build())
        .toList();
  }

  private Integer getVegosszeg(Szamla szamla) {
    return szamla.getTetelek().stream()
        .mapToInt(tetel -> tetel.getRendeles().getEtteremTermek().getAr() * (tetel.getRendeles().getMennyiseg()))
        .sum() / 100 * 115;
  }

  private Integer getFizetettVegosszeg(Szamla szamla) {
    return szamla.getTetelek().stream()
        .mapToInt(tetel -> tetel.getRendeles().getEtteremTermek().getAr() * tetel.getFizetettMennyiseg())
        .sum() / 100 * 115;
  }

  public void splitSzamla(Szamla szamla) {
    szamla.setSplit(true);
  }

  public void cancelSplit(Integer asztalId) {
    createSzamlaForAsztal(asztalId);
    asztalService.getById(asztalId).getSzamla().setSplit(false);
  }

  public void addToSzamlaSplit(Integer asztalId, Integer termekId) {
    Szamla szamla = findSzamlaByAsztalId(asztalId);
    SzamlaTetel szamlaTetel = szamla.getTetelek().stream()
        .filter(tetel -> tetel.getRendeles().getEtteremTermek().getId() == termekId)
        .findFirst()
        .orElseThrow();
    szamlaTetel.setFizetettMennyiseg(szamlaTetel.getFizetettMennyiseg() + 1);
    szamlaTetel.setNemFizetettMennyiseg(szamlaTetel.getNemFizetettMennyiseg() - 1);
  }

  public void szamlaTetelEltavolitasa(Integer asztalId, Rendeles rendeles) {
    Asztal asztal = asztalService.getById(asztalId);
    SzamlaTetel szamlaTetel = asztal.getSzamla().getTetelek().stream()
        .filter(tetel -> tetel.getRendeles().equals(rendeles))
        .findFirst()
        .orElseThrow();
    asztal.getSzamla().getTetelek().remove(szamlaTetel);
    szamlaTetelRepository.delete(szamlaTetel);
  }
}
