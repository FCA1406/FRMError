package br.com.decade.frmerror.server;


public class FRMErrorBO {
    private String code;
    private String erro;
    private String cause;
    private String action;
    private String appears;
    private String nivel;
    private String gatilho;
    private String favorite;

    public FRMErrorBO() {
        super();
    }
/*
    public FRMErrorBO(String code, String erro, String cause, String action, String appears, String nivel, String gatilho, String favorite) {
        super();

        this.code = code;
        this.erro = erro;
        this.cause = cause;
        this.action = action;
        this.appears = appears;
        this.nivel = nivel;
        this.gatilho = gatilho;
        this.favorite = favorite;
    }
*/
    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setErro(String erro) {
        this.erro = erro;
    }

    public String getErro() {
        return erro;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getCause() {
        return cause;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public void setAppears(String appears) {
        this.appears = appears;
    }

    public String getAppears() {
        return appears;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getNivel() {
        return nivel;
    }

    public void setGatilho(String gatilho) {
        this.gatilho = gatilho;
    }

    public String getGatilho() {
        return gatilho;
    }

    public void setFavorite(String favorite) {
        this.favorite = favorite;
    }

    public String getFavorite() {
        return favorite;
    }
}
