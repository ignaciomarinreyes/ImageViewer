package persistence;

import java.io.*;
import model.Image;

public class FileImageLoader implements ImageLoader {
    private final File[] files;

    public FileImageLoader(File folder) {
        this.files = folder.listFiles(imageTypes());
    }

    private FileFilter imageTypes() {
        return new FileFilter() {
            
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().endsWith(".JPG");
            }
        };
    }

    @Override
    public Image load() {
        return imageAt(0);
    }

    private Image imageAt(int i) {
        return new Image() {
            @Override
            public String name() {
                return files[i].getName();
            }

            @Override
            public InputStream stream() {
                try {
                    return new BufferedInputStream(
                            new FileInputStream(files[i]));
                } catch (FileNotFoundException e) {
                    return null;
                }
            }

            @Override
            public Image next() {
                return i == files.length - 1 ? imageAt(0) : imageAt(i+1);
            }

            @Override
            public Image prev() {
                return i == 0 ? imageAt(files.length - 1) : imageAt(i-1);
            }
        };
    }
    
}
