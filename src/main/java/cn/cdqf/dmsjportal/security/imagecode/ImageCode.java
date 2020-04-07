package cn.cdqf.dmsjportal.security.imagecode;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ImageCode implements Serializable {

    private BufferedImage bufferedImage;
    private String code;
    private LocalDateTime localDateTime;

    public ImageCode(BufferedImage bufferedImage, String code, int expire) {
        this.bufferedImage = bufferedImage;
        this.code = code;
        //在当前时间加上过期时间
        this.localDateTime = LocalDateTime.now().plusSeconds(expire);
    }
        //这个方法是判断验证码过期了
    public boolean isExpire(){

        return LocalDateTime.now().isAfter(localDateTime);
    }

}
