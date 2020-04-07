package cn.cdqf.dmsjportal.common;

public interface Constant {
    interface  Globle{
        String HTML_STYLE = "html";

        String LOGIN_SUCCESS_JSON_STYLE="json";
    }
    interface DmsjUser{
        String USERNAME_REPEAT ="用户已经存在";
        String LOGIN_USER_KEY="LOGIN_USER_KEY";
        String LOGIN_URL="/aaa/login";
        String LOGIN_URL_MOBILE="/aaa/mobile";
        String REDIS_USER_ATTENTION_PROJECT_IDS="REDIS_USER_ATTENTION_PROJECT_IDS";
    }

    interface Security{
        String  IMAGE_CODE_KEY="image_code_key";
        String DMSJ_PHONE_CODE="DMSJ_PHONE_CODE";
    }
    interface  Project{
        String REDIS_PROJECT_ATTENTION_KEY="REDIS_PROJECT_ATTENTION_KEY_";
    }
    interface UserAttentionParam{
        String REDIS_HASH_PROJECTID  ="projectId";
        String REDIS_HASH_PROJECTNAME="projectName";
        String REDIS_HASH_COMPLETION ="completion";
        String REDIS_HASH_RESIDUEDAY ="residueDay";
        String REDIS_HASH_SUPPORTMONEY="supportMoney";
        String REDIS_HASH_SUPPORTER="supporter";
        String REDIS_HASH_ATTENTION="attention";
    }

}
