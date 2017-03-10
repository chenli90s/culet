package utils;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Chenli
 * @time 2017/3/10 9:34
 */
public class UploadFilesUtils {
    /**
     *
     * @param request
     * @param path  upload/--/--/
     * @return
     * @throws IOException
     */
    public String upLoadFiles(HttpServletRequest request ,String path) throws IOException {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if (resolver.isMultipart(request)){
            MultipartHttpServletRequest multipartrequest = (MultipartHttpServletRequest) request;
            Iterator<String> fileNames = multipartrequest.getFileNames();
            while (fileNames.hasNext()){
                MultipartFile file = multipartrequest.getFile(fileNames.next());
                if (file != null) {
                    path = request.getSession().getServletContext().getRealPath(path);
                    String filename = UUIDUtils.getUUIDHex() + file.getName().
                            substring(file.getName().indexOf("."));
                    file.transferTo(new File(path+filename));
                    return filename;
                }
            }
        }
        return null;
    }
}
