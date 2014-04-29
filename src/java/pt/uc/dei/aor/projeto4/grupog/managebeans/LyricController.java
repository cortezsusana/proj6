/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pt.uc.dei.aor.projeto4.grupog.managebeans;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import pt.uc.dei.aor.projeto4.grupog.ejbs.LyricFacade;
import pt.uc.dei.aor.projeto4.grupog.ejbs.MusicFacade;
import pt.uc.dei.aor.projeto4.grupog.entities.Lyric;
import pt.uc.dei.aor.projeto4.grupog.entities.Music;
import pt.uc.dei.aor.projeto4.grupog.jsf.util.JsfUtil;

/**
 *
 * @author Elsa Susana
 */
@Named(value = "lyricController")
@ViewScoped
public class LyricController {

    @Inject
    private LyricFacade lyricFacade;
    @Inject
    private MusicFacade musicFacade;
    @Inject
    private LoggedUserMb loggedUserMb;
    @Inject
    private GeneralController generalController;
    @Inject
    private WebServiceController webServiceController;

    private Lyric lyric, selectLyric, updatedLyric;
    private String originalLyric;
    private Music music;
    private Integer musicId;

    /**
     * Creates a new instance of LyricController
     */
    public LyricController() {
    }

    @PostConstruct
    public void init() {
        this.lyric = new Lyric();
    }

    public boolean findLyric() {
        try {
            this.selectLyric = lyricFacade.findLyric(music, loggedUserMb.getUser());
            return true;
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e.getMessage());
        }
        return false;
    }

    public String prepareEdit(Music m) {
        if (m.getUser().equals(loggedUserMb.getUser())) {
            this.music = m;
            return "editLyric";
        } else {
            JsfUtil.addErrorMessage("You don´t have permission to change this music");
            return null;
        }
    }

    public void editLyric() {

    }

    public LyricFacade getLyricFacade() {
        return lyricFacade;
    }

    public void setLyricFacade(LyricFacade lyricFacade) {
        this.lyricFacade = lyricFacade;
    }

    public Lyric getLyric() {
        return lyric;
    }

    public void setLyric(Lyric lyric) {
        this.lyric = lyric;
    }

    public Lyric getSelectLyric() {
        return selectLyric;
    }

    public void setSelectLyric(Lyric selectLyric) {
        this.selectLyric = selectLyric;
    }

    public LoggedUserMb getLoggedUserMb() {
        return loggedUserMb;
    }

    public void setLoggedUserMb(LoggedUserMb loggedUserMb) {
        this.loggedUserMb = loggedUserMb;
    }

    public GeneralController getGeneralController() {
        return generalController;
    }

    public void setGeneralController(GeneralController generalController) {
        this.generalController = generalController;
    }

    public WebServiceController getWebServiceController() {
        return webServiceController;
    }

    public void setWebServiceController(WebServiceController webServiceController) {
        this.webServiceController = webServiceController;
    }

    public Lyric getUpdatedLyric() {
        return updatedLyric;
    }

    public void setUpdatedLyric(Lyric updatedLyric) {
        this.updatedLyric = updatedLyric;
    }

    public String getOriginalLyric() {
        return originalLyric;
    }

    public void setOriginalLyric(String originalLyric) {
        this.originalLyric = originalLyric;
    }

    public Music getMusic() {
        return music;
    }

    public void setMusic(Music music) {
        this.music = music;
    }

    public MusicFacade getMusicFacade() {
        return musicFacade;
    }

    public void setMusicFacade(MusicFacade musicFacade) {
        this.musicFacade = musicFacade;
    }

    public Integer getMusicId() {
        return musicId;
    }

    public void setMusicId(Integer musicId) {
        this.musicId = musicId;
    }

    public void chartLyricSOAP(Music m) {
        if (!findLyric()) {
            this.originalLyric = webServiceController.getLyricSong(m);
            music = m;
        }
    }

    public void chartLyricREST(Music m) {
        if (!findLyric()) {
            //this.originalLyric = webServiceController.;
            music = m;
        }
    }

    public void lyricWikiSOAP(Music m) {
        if (!findLyric()) {
            try {
                this.originalLyric = webServiceController.songLyricWikiSOAP(m);
                music = m;
            } catch (RemoteException ex) {
                Logger.getLogger(LyricController.class.getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex.getMessage());
            }
        }
    }

    public void save() {
        if (!findLyric()) {
            lyric = new Lyric();
            lyric.setAppuser(loggedUserMb.getUser());
            lyric.setMusic(music);
            lyric.setTextLyric(originalLyric);
            lyricFacade.create(lyric);
        } else {
            JsfUtil.addSuccessMessage("You already have a lyric og this music");
        }
    }
}
