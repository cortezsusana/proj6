/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pt.uc.dei.aor.projeto4.grupog.managebeans;

import com.LyricWiki.LyricWikiPortType_Stub;
import com.LyricWiki.LyricWiki_Impl;
import com.LyricWiki.LyricsResult;
import com.chartlyrics.api.GetLyricResult;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import pt.uc.dei.aor.projeto4.grupog.WebService.ChartLyricSoap;
import pt.uc.dei.aor.projeto4.grupog.WebService.LyricsWikiSoap;
import pt.uc.dei.aor.projeto4.grupog.entities.Music;
import pt.uc.dei.aor.projeto4.grupog.jsf.util.JsfUtil;

/**
 *
 * @author Bruno Maricatod
 */
@Named
@RequestScoped
public class WebServiceController {

    private RequestMusicMb musicBB;
    private String result;
    private boolean disable;

    private ChartLyricSoap chartLyricSoap;
    private LyricsWikiSoap lyricsWikiSoap;

    /**
     * Creates a new instance of WebServiceController
     */
    public WebServiceController() {
    }
//---ChartLyric---

    public String getLyricSong(Music m) {
        GetLyricResult glr = searchLyricDirect(m.getArtist(), m.getTitle());
        return this.result = glr.getLyric();
    }

    private static GetLyricResult searchLyricDirect(java.lang.String artist, java.lang.String song) {
        com.chartlyrics.api.Apiv1 service = new com.chartlyrics.api.Apiv1();
        com.chartlyrics.api.Apiv1Soap port = service.getApiv1Soap();
        return port.searchLyricDirect(artist, song);
    }

    //---LyricWiki---
    public String songLyric(String artist, String song) throws RemoteException {
        LyricWikiPortType_Stub lw = createProxy();
        LyricsResult lr = lw.getSong(artist, song);
        return lr.getLyrics();
    }

    private static LyricWikiPortType_Stub createProxy() {
        return (LyricWikiPortType_Stub) (new LyricWiki_Impl().getLyricWikiPort());
    }

    public boolean checkSongExists(Music m) throws RemoteException {
        boolean existSong = false;
        try {
            LyricWikiPortType_Stub lwpts = createProxy();
            existSong = lwpts.checkSongExists(m.getArtist(), m.getTitle());
        } catch (RemoteException ex) {
            Logger.getLogger(WebServiceController.class.getName()).log(Level.SEVERE, null, ex);
            JsfUtil.addErrorMessage(ex.getMessage());
        }
        return existSong;
    }

    public String buttonLyric(Music m) {
        try {
            if (checkSongExists(m)) {
                return "yes";
            }
        } catch (RemoteException ex) {
            Logger.getLogger(WebServiceController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "no";
    }

    //---GETTERS E SETTERS
    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public RequestMusicMb getMusicBB() {
        return musicBB;
    }

    public void setMusicBB(RequestMusicMb musicBB) {
        this.musicBB = musicBB;
    }

    public ChartLyricSoap getChartLyricSoap() {
        return chartLyricSoap;
    }

    public void setChartLyricSoap(ChartLyricSoap chartLyricSoap) {
        this.chartLyricSoap = chartLyricSoap;
    }

    public boolean disable(Music m) {
        try {
            return !checkSongExists(m);
        } catch (RemoteException ex) {
            Logger.getLogger(WebServiceController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
