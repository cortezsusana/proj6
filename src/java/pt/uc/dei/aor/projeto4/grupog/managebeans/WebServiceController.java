/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.uc.dei.aor.projeto4.grupog.managebeans;

import com.LyricWiki.LyricWikiPortType_Stub;
import com.LyricWiki.LyricWiki_Impl;
import com.LyricWiki.LyricsResult;
import com.chartlyrics.api.Apiv1;
import com.chartlyrics.api.GetLyricResult;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.xml.ws.WebServiceRef;
import pt.uc.dei.aor.projeto4.grupog.entities.Music;

/**
 *
 * @author Bruno Maricatod
 */
@Named
@RequestScoped
public class WebServiceController {

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/ChartLyrics.wsdl")
    private com.chartlyrics.api.Apiv1 service;

    private RequestMusicMb musicBB;
    private String webservice;
    private Music m;
    private boolean disable;
    private Music music;
    private String result;
    private Client client;

    @Inject
    private GeneralController generalController;

    /**
     * Creates a new instance of WebServiceController
     */
    public WebServiceController() {
    }

    @PostConstruct
    public void init() {
        result = "";
        client = ClientBuilder.newClient();
    }

    //---ChartLyric---
    public String getLyricSong(Music m) {
        GetLyricResult glr = searchLyricDirect(m.getArtist(), m.getTitle());
        return this.result = glr.getLyric();
    }

    private com.chartlyrics.api.GetLyricResult searchLyricDirect(java.lang.String artist, java.lang.String song) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        com.chartlyrics.api.Apiv1Soap port = service.getApiv1Soap();
        return port.searchLyricDirect(artist, song);
    }

    public String songRESTResult(Music m) {

        this.result = client.target("http://api.chartlyrics.com/apiv1.asmx/SearchLyricDirect")
                .queryParam("artist", m.getArtist())
                .queryParam("song", m.getTitle())
                .request(MediaType.TEXT_PLAIN)
                .get(String.class);
        return result;
    }

    //---LyricWiki---
    public String songLyricWikiSOAP(Music m) throws RemoteException {
        LyricWikiPortType_Stub lw = createProxy();
        LyricsResult lr = lw.getSong(m.getArtist(), m.getTitle());
        return this.result = lr.getLyrics();
    }

    public String lyricRESTResult(Music m) {
        Client client = ClientBuilder.newClient();
        result = client.target("http://lyrics.wikia.com/api.php")
                .queryParam("func", "getSong")
                .queryParam("artist", m.getArtist())
                .queryParam("song", m.getTitle())
                .queryParam("fmt", "text")
                .request(MediaType.TEXT_PLAIN)
                .get(String.class);
        return result;
    }

    private static LyricWikiPortType_Stub createProxy() {
        return (LyricWikiPortType_Stub) (new LyricWiki_Impl().getLyricWikiPort());
    }

    public boolean checkSongExists(Music m) throws RemoteException {
        boolean existSong = false;
        try {
            LyricWikiPortType_Stub lwpts = createProxy();
            existSong = lwpts.checkSongExists(m.getArtist(), m.getTitle());
            return existSong;
        } catch (RemoteException ex) {
            Logger.getLogger(WebServiceController.class.getName()).log(Level.SEVERE, null, ex);
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

    //---GETTERS E SETTERS
    public RequestMusicMb getMusicBB() {
        return musicBB;
    }

    public void setMusicBB(RequestMusicMb musicBB) {
        this.musicBB = musicBB;
    }

    public String getWebservice() {
        return webservice;
    }

    public void setWebservice(String webservice) {
        this.webservice = webservice;
    }

    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }

    public Music getMusic() {
        return music;
    }

    public void setMusic(Music music) {
        this.music = music;
    }

    public boolean disable(Music m) {
        try {
            return !checkSongExists(m);
        } catch (RemoteException ex) {
            Logger.getLogger(WebServiceController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public Apiv1 getService() {
        return service;
    }

    public void setService(Apiv1 service) {
        this.service = service;
    }

    public Music getM() {
        return m;
    }

    public void setM(Music m) {
        this.m = m;
    }

    public GeneralController getGeneralController() {
        return generalController;
    }

    public void setGeneralController(GeneralController generalController) {
        this.generalController = generalController;
    }

    public void setResult(String result) {
        this.result = result;
    }

}
