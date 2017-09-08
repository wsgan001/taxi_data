package com.nwu.data.taxi.service.helper;
import com.nwu.data.taxi.domain.repository.GPSDataRepository;
import com.nwu.data.taxi.domain.repository.GPSReadingRepository;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;

public class RawInputProcessor {
    private String extension;

    public RawInputProcessor( String e) {
        setExtension(e);
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public void processFiles(byte fromStatus, byte toStatus, GPSReadingRepository gpsReadingRepository){
        File dir = Config.getDataFolder();
        if (dir.isDirectory()) {
            File[] files = dir.listFiles(Config.getFileFilter());
            int cnt=1;
            for (File f : files){
                gpsReadingRepository.save(new FileRawInputProcessor ().process(f, fromStatus, toStatus));
                System.out.println( "processed " + cnt++ + " out of " + files.length );
            }
        }
    }

    public void processFiles(GPSDataRepository gpsDataRepository, Integer index) {
        File dir;
        try {
            dir = new ClassPathResource("data/"+index).getFile();
            if (dir.isDirectory()) {
                File[] files = dir.listFiles(Config.getFileFilter());
                int cnt=1;
                for (File f : files){
                    gpsDataRepository.save(new FileRawInputProcessor ().process(f));
                    System.out.println( "processed " + cnt++ + " out of " + files.length );
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
