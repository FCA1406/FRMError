package br.com.decade.frmerror.mobile;

import br.com.decade.frmerror.database.ConnectionFactory;
import br.com.decade.frmerror.server.FRMErrorBO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;

import oracle.adfmf.framework.api.AdfmfContainerUtilities;
import oracle.adfmf.java.beans.ProviderChangeListener;
import oracle.adfmf.java.beans.ProviderChangeSupport;
import oracle.adfmf.util.Utility;

public class FRMErrorDC {
    private FRMErrorBO[] FRMErrorVO;

    private ProviderChangeSupport providerChangeSupport = new ProviderChangeSupport(this);

    public FRMErrorDC() {
        super();

        FRMErrorVO = getFRMErrorDB(new String(""));
    }

    public void setFRMErrorVO(FRMErrorBO[] FRMErrorVO) {
        this.FRMErrorVO = FRMErrorVO;
        providerChangeSupport.fireProviderRefresh("FRMErrorVO");
    }

    public FRMErrorBO[] getFRMErrorVO() {
        return FRMErrorVO;
    }

    private FRMErrorBO[] getFRMErrorDB(String findString) {
        List listError = new ArrayList();

        String doDML;

        if (findString.equals("")) {
            doDML = "SELECT code, erro, cause, action, appears, nivel, gatilho, favorite FROM frm_error WHERE favorite = 'Y' ORDER BY code;";
        } else {
            doDML = "SELECT code, erro, cause, action, appears, nivel, gatilho, favorite FROM frm_error WHERE code LIKE '%" + findString + "%' OR erro LIKE '%" + findString + "%' ORDER BY code;";
        }

        try {
            Connection conn = ConnectionFactory.getConnection();
            conn.setAutoCommit(false);

            Statement stFRMError = conn.createStatement();
            ResultSet rsFRMError = stFRMError.executeQuery(doDML);

            while (rsFRMError.next()) {
                FRMErrorBO frmError = new FRMErrorBO();

                frmError.setCode(rsFRMError.getString("code"));
                frmError.setErro(rsFRMError.getString("erro"));
                frmError.setCause(rsFRMError.getString("cause"));
                frmError.setAction(rsFRMError.getString("action"));
                frmError.setAppears(rsFRMError.getString("appears"));
                frmError.setNivel(rsFRMError.getString("nivel"));
                frmError.setGatilho(rsFRMError.getString("gatilho"));
                frmError.setFavorite(rsFRMError.getString("favorite"));

                listError.add(frmError);
            }
        } catch (Exception ex) {
            Utility.ApplicationLogger.severe(ex.getMessage());

            AdfmfContainerUtilities.invokeContainerJavaScriptFunction("main"
                                                                     ,"navigator.notification.alert"
                                                                     ,new Object[] { "DB: IS DOWN OR DEAD", "null", "Warning", "OK" });
        }

        return (FRMErrorBO[]) listError.toArray(new FRMErrorBO[listError.size()]);
    }

    public void findError (String frmError) {
        setFRMErrorVO(getFRMErrorDB(frmError));
    }

    public void makeFavorite (String code, String favorite) {
        try {
            Connection conn = ConnectionFactory.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement psDML = conn.prepareStatement("UPDATE frm_error SET favorite = ? WHERE code = ?;");

            psDML.setString(1, favorite);
            psDML.setString(2, code);

            psDML.execute();

            conn.commit();
        } catch (Exception ex) {
            Utility.ApplicationLogger.severe(ex.getMessage());

            AdfmfContainerUtilities.invokeContainerJavaScriptFunction("main"
                                                                     ,"navigator.notification.alert"
                                                                     ,new Object[] { "DB: IS DOWN OR DEAD", "null", "Warning", "OK" });
        }
    }

    public void addProviderChangeListener(ProviderChangeListener l) {
        providerChangeSupport.addProviderChangeListener(l);
    }

    public void removeProviderChangeListener(ProviderChangeListener l) {
        providerChangeSupport.removeProviderChangeListener(l);
    }
}
