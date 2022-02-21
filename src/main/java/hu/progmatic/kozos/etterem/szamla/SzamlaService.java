package hu.progmatic.kozos.etterem.szamla;

import hu.progmatic.kozos.etterem.leltar.TermekDto;
import hu.progmatic.kozos.etterem.rendeles.Rendeles;
import hu.progmatic.kozos.etterem.rendeles.RendelesDto;
import hu.progmatic.kozos.etterem.rendeles.RendelesRepository;
import hu.progmatic.kozos.felhasznalo.FelhasznaloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import hu.progmatic.kozos.etterem.asztal.Asztal;
import hu.progmatic.kozos.etterem.asztal.AsztalService;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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
  @Autowired
  private RendelesRepository rendelesRepository;
  @Autowired
  private FelhasznaloService felhasznaloService;

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

  public SzamlaDto buildSzamlaDto(Integer asztalId) {
    return szamlaDtoBuilder(asztalService.getById(asztalId).getSzamla());
  }

  public SzamlaDto szamlaDtoBuilder(Szamla szamla) {
    return SzamlaDto.builder()
        .id(szamla.getId())
        .asztalId(szamla.getAsztal().getId())
        .vegosszeg(getVegosszeg(szamla))
        .fizetettVegosszeg(getFizetettVegosszeg(szamla))
        .tetelek(getTetelDtoList(szamla.getTetelek()))
        .split(szamla.isSplit())
        .asztalSzam(szamla.getAsztal().getAsztalSzam())
        .build();
  }

  private List<SzamlaTetelDto> getTetelDtoList(List<SzamlaTetel> tetelek) {
    return tetelek.stream()
        .map(tetel -> SzamlaTetelDto.builder()
            .id(tetel.getId())
            .rendelesDto(RendelesDto.builder()
                .id(tetel.getRendeles().getId())
                .termekDto(TermekDto.builder()
                    .id(tetel.getRendeles().getTermek().getId())
                    .nev(tetel.getRendeles().getTermek().getNev())
                    .ar(tetel.getRendeles().getTermek().getAr())
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
        .mapToInt(tetel -> tetel.getRendeles().getTermek().getAr() * tetel.getNemFizetettMennyiseg())
        .sum() / 100 * 115;
  }

  private Integer getFizetettVegosszeg(Szamla szamla) {
    return szamla.getTetelek().stream()
        .mapToInt(tetel -> tetel.getRendeles().getTermek().getAr() * tetel.getFizetettMennyiseg())
        .sum() / 100 * 115;
  }

  public void splitSzamla(Integer asztalId) {
    asztalService.getById(asztalId).getSzamla().setSplit(true);
  }

  public void cancelSplit(Integer asztalId) {
    createSzamlaForAsztal(asztalId);
    asztalService.getById(asztalId).getSzamla().setSplit(false);
  }

  public void addToSzamlaSplit(Integer asztalId, Integer termekId) {
    Szamla szamla = findSzamlaByAsztalId(asztalId);
    SzamlaTetel szamlaTetel = szamla.getTetelek().stream()
        .filter(tetel -> tetel.getRendeles().getTermek().getId() == termekId)
        .findFirst()
        .orElseThrow();
    szamlaTetel.setFizetettMennyiseg(szamlaTetel.getFizetettMennyiseg() + 1);
    szamlaTetel.setNemFizetettMennyiseg(szamlaTetel.getNemFizetettMennyiseg() - 1);
  }

  public void removeFromSzamlaSplit(Integer asztalId, Integer termekId) {
    Szamla szamla = findSzamlaByAsztalId(asztalId);
    SzamlaTetel szamlaTetel = szamla.getTetelek().stream()
        .filter(tetel -> tetel.getRendeles().getTermek().getId() == termekId)
        .findFirst()
        .orElseThrow();
    szamlaTetel.setFizetettMennyiseg(szamlaTetel.getFizetettMennyiseg() - 1);
    szamlaTetel.setNemFizetettMennyiseg(szamlaTetel.getNemFizetettMennyiseg() + 1);
  }

  public void szamlaTetelEltavolitasa(Integer asztalId, Integer rendelesId) {
    Asztal asztal = asztalService.getById(asztalId);
    SzamlaTetel szamlaTetel = asztal.getSzamla().getTetelek().stream()
        .filter(tetel -> tetel.getRendeles().getId() == rendelesId)
        .findFirst()
        .orElseThrow();
    asztal.getSzamla().getTetelek().remove(szamlaTetel);
    szamlaTetelRepository.delete(szamlaTetel);
  }

  public void kulonSzamlaFizetese(Integer asztalId) {
    List<SzamlaTetel> eltavolitandoTetelek = new ArrayList<>();
    List<Rendeles> eltavolitandoRendelesek = new ArrayList<>();
    Asztal asztal = asztalService.getById(asztalId);
    for (SzamlaTetel tetel : asztal.getSzamla().getTetelek()) {
      for (Rendeles rendeles : asztal.getRendelesek()) {
        if (tetel.getRendeles().getTermek().equals(rendeles.getTermek())) {
          rendeles.setMennyiseg(rendeles.getMennyiseg() - tetel.getFizetettMennyiseg());
          tetel.setFizetettMennyiseg(0);
        }
        if (rendeles.getMennyiseg() == 0) {
          eltavolitandoTetelek.add(tetel);
          eltavolitandoRendelesek.add(rendeles);
        }
      }
    }
    asztal.getSzamla().getTetelek().removeAll(eltavolitandoTetelek);
    asztal.getRendelesek().removeAll(eltavolitandoRendelesek);
    szamlaTetelRepository.deleteAll(eltavolitandoTetelek);
    rendelesRepository.deleteAll(eltavolitandoRendelesek);
    asztal.getSzamla().setSplit(false);
  }

  public void szamlaFizetese(Integer asztalId) {
    Asztal asztal = asztalService.getById(asztalId);
    asztal.getSzamla().getTetelek().removeAll(asztal.getSzamla().getTetelek());
    szamlaTetelRepository.deleteAllBySzamla(asztal.getSzamla());
    rendelesRepository.deleteAll(asztal.getRendelesek());
    asztal.getRendelesek().removeAll(asztal.getRendelesek());
  }

  public String szamlaFileNev(Szamla szamla) {
    String asztalNev = szamla.getAsztal().getNev();
    Integer szamlaid = szamla.getId();
    Integer split = 0;
    List<SzamlaTetel> tetelek = szamla.getTetelek();
    int vegosszeg = 0;
    for (SzamlaTetel tetel : tetelek) {
      if (szamla.isSplit() && tetel.getFizetettMennyiseg() > 0) {
        split = tetel.getId();
        vegosszeg = getFizetettVegosszeg(szamla);
      } else if (!szamla.isSplit()) {
        vegosszeg = getVegosszeg(szamla);
      }
    }
    return asztalNev + " " + szamlaid + "-" + split + ".txt";
  }
  public String szamlaFileTartalom(Szamla szamla) {
    String asztalNev = szamla.getAsztal().getNev();
    List<SzamlaTetel> tetelek = szamla.getTetelek();
    String felhasznalo = felhasznaloService.getById(felhasznaloService.getFelhasznaloId()).getNev();
    String szamlaString = asztalNev + "\n Felszolgáló:"+felhasznalo+"\n\n";
    int vegosszeg = 0;
    for (SzamlaTetel tetel : tetelek) {
      if (szamla.isSplit() && tetel.getFizetettMennyiseg() > 0) {
        vegosszeg = getFizetettVegosszeg(szamla);
        szamlaString += tetel.getRendeles().getTermek().getNev() + " "
                + tetel.getFizetettMennyiseg() + " "
                + tetel.getRendeles().getTermek().getAr() + "\n";
      } else if (!szamla.isSplit()) {
        vegosszeg = getVegosszeg(szamla);
        szamlaString += tetel.getRendeles().getTermek().getNev() + " "
                + tetel.getNemFizetettMennyiseg() + " "
                + tetel.getRendeles().getTermek().getAr() + "\n";
      }
    }
    int servizDij = vegosszeg / 115 * 15;
    szamlaString += "\nSzerviz díj: " + servizDij +
            "\nVégösszeg: " + vegosszeg;
    return szamlaString;
  }
}
