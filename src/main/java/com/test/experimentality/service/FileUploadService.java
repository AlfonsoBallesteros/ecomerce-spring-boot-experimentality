package com.test.experimentality.service;

import com.test.experimentality.web.errors.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.time.Clock;
import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileUploadService {

    private final Clock clock;

    private final ResourceLoader resourceLoader;

    public String uploadImages(MultipartFile image){
        return saveImage(image, clock);
    }

    public String saveImage (MultipartFile file, Clock clock){
        try{
            String type = getType(file.getContentType());
            long epoch = Instant.now(clock).toEpochMilli();
            String filename = getFilename(epoch, type);
            BufferedImage image = ImageIO.read(file.getInputStream());
            BufferedImage scaled = scale(1200 , 1200, image);
            ImageIO.write(scaled, type, getLocation(filename));
            return filename;
        }catch(IOException e){
            e.getStackTrace();
            throw new BadRequestException("P-500", e.getMessage());
        }
    }

    private File getLocation(String filename) throws IOException {
        return new File(resourceLoader.getResource("file:./filestorage/").getFile(), filename);
    }

    public BufferedImage scale(int maxWidth, int maxHeight, BufferedImage image) {
        if (image.getWidth() >= image.getHeight() && image.getWidth() > maxWidth) {
            int newHeight = (int) (image.getHeight() * ((float) maxWidth / image.getWidth()));
            return getBufferered(image.getScaledInstance(maxWidth, newHeight, BufferedImage.SCALE_SMOOTH), maxWidth, newHeight);
        } else if (image.getHeight() > image.getWidth() && image.getHeight() > maxHeight) {
            int newWidth = (int) (image.getWidth() * ((float) maxHeight / image.getHeight()));
            return getBufferered(image.getScaledInstance(newWidth, maxHeight, BufferedImage.SCALE_SMOOTH), newWidth, maxHeight);
        } else {
            return image;
        }
    }

    private BufferedImage getBufferered(Image image, int width, int height) {
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        bufferedImage.createGraphics().drawImage(image, 0, 0, null);
        return bufferedImage;
    }

    public String getFilename(long epoch, String type) {
        return MessageFormat.format("{0}_{1}.{2}", UUID.randomUUID().toString().replace("-","").substring(0,8).toUpperCase(), String.valueOf(epoch), type);
    }

    private String getType(String mimetype) {
        MediaType mediaType = MediaType.parseMediaType(mimetype);
        if (!isImage(mediaType)) throw new BadRequestException("P-500", "Invalid content-type");
        else return mediaType.getSubtype();
    }

    private boolean isJpeg(MediaType mediaType) {
        return "jpeg".equalsIgnoreCase(mediaType.getSubtype());
    }

    private boolean isImage(MediaType mediaType) {
        return "image".equalsIgnoreCase(mediaType.getType());
    }

}
