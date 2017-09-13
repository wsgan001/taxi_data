package com.nwu.data.taxi.service.helper;
import com.nwu.data.taxi.domain.repository.GPSDataRepository;
import com.nwu.data.taxi.domain.repository.GPSReadingRepository;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;

public class GPSDataProcessor {
    private String extension;

    public GPSDataProcessor(String e) {
        setExtension(e);
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public void processFiles(byte fromStatus, byte toStatus, GPSReadingRepository gpsReadingRepository){
        File dir = Config.getDataFolder();
        if (dir.isDirectory()) {
            File[] files = dir.listFiles(Config.FILE_FILTER);
            int cnt=1;
            for (File f : files){
                gpsReadingRepository.save(new GPSDataFileProcessor().process(f, fromStatus, toStatus));
                System.out.println( "processed " + cnt++ + " out of " + files.length );
            }
        }
    }

    public void processFiles(GPSDataRepository gpsDataRepository, Integer index) {
        File dir;
        try {
            dir = new ClassPathResource("data/"+index).getFile();
            if (dir.isDirectory()) {
                File[] files = dir.listFiles(Config.FILE_FILTER);
                int cnt=1;
                for (File f : files){
                    gpsDataRepository.save(new GPSDataFileProcessor().process(f));
                    System.out.println( "processed " + cnt++ + " out of " + files.length );
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
