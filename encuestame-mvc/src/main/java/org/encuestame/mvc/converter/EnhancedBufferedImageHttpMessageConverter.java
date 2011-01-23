package org.encuestame.mvc.converter;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotWritableException;

public class EnhancedBufferedImageHttpMessageConverter extends BufferedImageHttpMessageConverter {

    private List<MediaType> writerMediaTypes = new ArrayList<MediaType>();

    public EnhancedBufferedImageHttpMessageConverter() {
        String[] writerMimeTypes = ImageIO.getWriterMIMETypes();
        for (String mimeType : writerMimeTypes) {
            try {
                writerMediaTypes.add(MediaType.parseMediaType(mimeType));
            } catch (IllegalArgumentException e) {
                // If the mimeType can't be parsed then it can't be a valid writerMediaType so ignore and go onto the next
            }
        }
    }

    @Override
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
        return (BufferedImage.class.equals(clazz) && isWritable(mediaType));
    }

    private boolean isWritable(MediaType mediaType) {
        if (mediaType == null) {
            return true;
        }

        for (MediaType writerMediaType : writerMediaTypes) {
            if (writerMediaType.isCompatibleWith(mediaType)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void write(BufferedImage image, MediaType contentType, HttpOutputMessage outputMessage) throws IOException,
                HttpMessageNotWritableException {
        MediaType checkedContentType = contentType;
        if (contentType.isWildcardType() || contentType.isWildcardSubtype()) {
            checkedContentType = null;
        }
        super.write(image, checkedContentType, outputMessage);
    }
}
