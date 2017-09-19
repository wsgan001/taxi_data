package com.nwu.data.taxi.service.helper.processor;
import com.nwu.data.taxi.domain.repository.GPSDataRepository;
import com.nwu.data.taxi.domain.repository.GPSReadingRepository;
import com.nwu.data.taxi.service.helper.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;

public class GPSDataProcessor {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
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
                logger.info( "processed " + cnt++ + " out of " + files.length );
            }
        }
    }

    public void processFiles(GPSDataRepository gpsDataRepository, int index) {
        File dir;
        try {
            dir = new ClassPathResource("data/"+index).getFile();
            if (dir.isDirectory()) {
                File[] files = dir.listFiles(Config.FILE_FILTER);
                int cnt=1;
                for (File f : files){
                    gpsDataRepository.save(new GPSDataFileProcessor().process(f));
                    logger.info( "processed " + cnt++ + " out of " + files.length );
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
