package com.wellnesshub.location;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@ConditionalOnProperty(prefix = "app.data", name = "init", havingValue = "true", matchIfMissing = false)
public class LocationDataInitializer implements CommandLineRunner {

    private final ProvinceRepository provinceRepository;
    private final DistrictRepository districtRepository;
    private final SectorRepository sectorRepository;
    private final CellRepository cellRepository;
    private final VillageRepository villageRepository;

    public LocationDataInitializer(ProvinceRepository provinceRepository,
                                 DistrictRepository districtRepository,
                                 SectorRepository sectorRepository,
                                 CellRepository cellRepository,
                                 VillageRepository villageRepository) {
        this.provinceRepository = provinceRepository;
        this.districtRepository = districtRepository;
        this.sectorRepository = sectorRepository;
        this.cellRepository = cellRepository;
        this.villageRepository = villageRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (provinceRepository.count() == 0) {
            initializeLocationData();
        }
    }

    private void initializeLocationData() {
        // Create Provinces
        Province kigali = new Province("KGL", "Kigali");
        Province northern = new Province("NTH", "Northern Province");
        Province southern = new Province("STH", "Southern Province");
        Province eastern = new Province("EST", "Eastern Province");
        Province western = new Province("WST", "Western Province");

        kigali = provinceRepository.save(kigali);
        northern = provinceRepository.save(northern);
        southern = provinceRepository.save(southern);
        eastern = provinceRepository.save(eastern);
        western = provinceRepository.save(western);

        // Create Districts for Kigali
        District gasabo = new District("KGL01", "Gasabo", kigali);
        District kicukiro = new District("KGL02", "Kicukiro", kigali);
        District nyarugenge = new District("KGL03", "Nyarugenge", kigali);

        gasabo = districtRepository.save(gasabo);
        kicukiro = districtRepository.save(kicukiro);
        nyarugenge = districtRepository.save(nyarugenge);

        // Create Districts for Northern Province
        District burera = new District("NTH01", "Burera", northern);
        District gakenke = new District("NTH02", "Gakenke", northern);
        District gicumbi = new District("NTH03", "Gicumbi", northern);
        District musanze = new District("NTH04", "Musanze", northern);
        District rulindo = new District("NTH05", "Rulindo", northern);

        burera = districtRepository.save(burera);
        gakenke = districtRepository.save(gakenke);
        gicumbi = districtRepository.save(gicumbi);
        musanze = districtRepository.save(musanze);
        rulindo = districtRepository.save(rulindo);

        // Create Sectors for Gasabo District
        Sector bumbogo = new Sector("KGL0101", "Bumbogo", gasabo);
        Sector gatsata = new Sector("KGL0102", "Gatsata", gasabo);
        Sector jali = new Sector("KGL0103", "Jali", gasabo);
        Sector gikomero = new Sector("KGL0104", "Gikomero", gasabo);
        Sector gisozi = new Sector("KGL0105", "Gisozi", gasabo);

        bumbogo = sectorRepository.save(bumbogo);
        gatsata = sectorRepository.save(gatsata);
        jali = sectorRepository.save(jali);
        gikomero = sectorRepository.save(gikomero);
        gisozi = sectorRepository.save(gisozi);

        // Create Sectors for Kicukiro District
        Sector gahanga = new Sector("KGL0201", "Gahanga", kicukiro);
        Sector gikondo = new Sector("KGL0202", "Gikondo", kicukiro);
        Sector kanombe = new Sector("KGL0203", "Kanombe", kicukiro);
        Sector kicukiro_sector = new Sector("KGL0204", "Kicukiro", kicukiro);
        Sector kigarama = new Sector("KGL0205", "Kigarama", kicukiro);

        gahanga = sectorRepository.save(gahanga);
        gikondo = sectorRepository.save(gikondo);
        kanombe = sectorRepository.save(kanombe);
        kicukiro_sector = sectorRepository.save(kicukiro_sector);
        kigarama = sectorRepository.save(kigarama);

        // Create Cells for Bumbogo Sector
        Cell bumbogo_cell1 = new Cell("KGL010101", "Bumbogo I", bumbogo);
        Cell bumbogo_cell2 = new Cell("KGL010102", "Bumbogo II", bumbogo);
        Cell bumbogo_cell3 = new Cell("KGL010103", "Bumbogo III", bumbogo);

        bumbogo_cell1 = cellRepository.save(bumbogo_cell1);
        bumbogo_cell2 = cellRepository.save(bumbogo_cell2);
        bumbogo_cell3 = cellRepository.save(bumbogo_cell3);

        // Create Cells for Gatsata Sector
        Cell gatsata_cell1 = new Cell("KGL010201", "Gatsata I", gatsata);
        Cell gatsata_cell2 = new Cell("KGL010202", "Gatsata II", gatsata);
        Cell gatsata_cell3 = new Cell("KGL010203", "Gatsata III", gatsata);

        gatsata_cell1 = cellRepository.save(gatsata_cell1);
        gatsata_cell2 = cellRepository.save(gatsata_cell2);
        gatsata_cell3 = cellRepository.save(gatsata_cell3);

        // Create Villages for Bumbogo Cell 1
        Village bumbogo_village1 = new Village("KGL01010101", "Bumbogo Village 1", bumbogo_cell1);
        Village bumbogo_village2 = new Village("KGL01010102", "Bumbogo Village 2", bumbogo_cell1);
        Village bumbogo_village3 = new Village("KGL01010103", "Bumbogo Village 3", bumbogo_cell1);

        villageRepository.save(bumbogo_village1);
        villageRepository.save(bumbogo_village2);
        villageRepository.save(bumbogo_village3);

        // Create Villages for Gatsata Cell 1
        Village gatsata_village1 = new Village("KGL01020101", "Gatsata Village 1", gatsata_cell1);
        Village gatsata_village2 = new Village("KGL01020102", "Gatsata Village 2", gatsata_cell1);
        Village gatsata_village3 = new Village("KGL01020103", "Gatsata Village 3", gatsata_cell1);

        villageRepository.save(gatsata_village1);
        villageRepository.save(gatsata_village2);
        villageRepository.save(gatsata_village3);

        // Create additional cells and villages for other sectors
        Cell jali_cell1 = new Cell("KGL010301", "Jali I", jali);
        Cell jali_cell2 = new Cell("KGL010302", "Jali II", jali);
        jali_cell1 = cellRepository.save(jali_cell1);
        jali_cell2 = cellRepository.save(jali_cell2);

        Village jali_village1 = new Village("KGL01030101", "Jali Village 1", jali_cell1);
        Village jali_village2 = new Village("KGL01030102", "Jali Village 2", jali_cell1);
        villageRepository.save(jali_village1);
        villageRepository.save(jali_village2);

        // Create cells and villages for Kicukiro sectors
        Cell gahanga_cell1 = new Cell("KGL020101", "Gahanga I", gahanga);
        Cell gahanga_cell2 = new Cell("KGL020102", "Gahanga II", gahanga);
        gahanga_cell1 = cellRepository.save(gahanga_cell1);
        gahanga_cell2 = cellRepository.save(gahanga_cell2);

        Village gahanga_village1 = new Village("KGL02010101", "Gahanga Village 1", gahanga_cell1);
        Village gahanga_village2 = new Village("KGL02010102", "Gahanga Village 2", gahanga_cell1);
        villageRepository.save(gahanga_village1);
        villageRepository.save(gahanga_village2);

        System.out.println("Location data initialized successfully!");
    }
}

