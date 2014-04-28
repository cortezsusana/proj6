/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pt.uc.dei.aor.projeto4.grupog.managebeans;

import com.chartlyrics.api.GetLyricResult;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import pt.uc.dei.aor.projeto4.grupog.entities.Music;

/**
 *
 * @author Bruno Maricatod
 */
@Named
@RequestScoped
public class WebServiceController {

    private RequestMusicMb musicBB;
    private String result;

    /**
     * Creates a new instance of WebServiceController
     */
    public WebServiceController() {
    }

    public boolean checkLyric(String artist, String song) {
        return !getLyricSong(artist, song).isEmpty();
    }

    public String buttonLyric(Music m) {
        if (m.isLyricExist()) {
            return "yes";
        }
        return "no";
    }

    public String getLyricSong(String artist, String song) {
        GetLyricResult glr = searchLyricDirect(artist, song);
        return this.result = glr.getLyric();
    }

    private static GetLyricResult searchLyricDirect(java.lang.String artist, java.lang.String song) {
        com.chartlyrics.api.Apiv1 service = new com.chartlyrics.api.Apiv1();
        com.chartlyrics.api.Apiv1Soap port = service.getApiv1Soap();
        return port.searchLyricDirect(artist, song);
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

}
