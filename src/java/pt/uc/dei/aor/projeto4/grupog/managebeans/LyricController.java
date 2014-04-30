/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.uc.dei.aor.projeto4.grupog.managebeans;

import java.io.Serializable;
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
public class LyricController implements Serializable {

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

    private Lyric lyric, selectLyric;
    private String originalLyric;
    private Music music;
    private Integer musicId;
    private boolean update;

    /**
     * Creates a new instance of LyricController
     */
    public LyricController() {
    }

    @PostConstruct
    public void init() {
        this.lyric = new Lyric();
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
        if (!lyricFacade.existLyric(m, loggedUserMb.getUser())) {
            this.originalLyric = webServiceController.getLyricSong(m);
            music = m;
            update = false;
        } else {
            try {
                this.selectLyric = lyricFacade.findLyric(m, loggedUserMb.getUser());
                this.originalLyric = webServiceController.getLyricSong(m);
                music = m;
                update = true;
            } catch (Exception ex) {
                Logger.getLogger(LyricController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void chartLyricREST(Music m) {
        if (!lyricFacade.existLyric(m, loggedUserMb.getUser())) {
            this.originalLyric = webServiceController.songRESTResult(m);
            music = m;
            update = false;
        } else {
            try {
                this.selectLyric = lyricFacade.findLyric(m, loggedUserMb.getUser());
                this.originalLyric = webServiceController.songRESTResult(m);
                music = m;
                update = true;
            } catch (Exception ex) {
                Logger.getLogger(LyricController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void lyricWikiSOAP(Music m) {
        if (!lyricFacade.existLyric(m, loggedUserMb.getUser())) {
            try {
                this.originalLyric = webServiceController.songLyricWikiSOAP(m);
                music = m;
                update = false;
            } catch (RemoteException ex) {
                Logger.getLogger(LyricController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                this.selectLyric = lyricFacade.findLyric(m, loggedUserMb.getUser());
                this.originalLyric = webServiceController.songLyricWikiSOAP(m);
                music = m;
                update = true;
            } catch (RemoteException ex) {
                Logger.getLogger(LyricController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(LyricController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void lyricWikiREST(Music m) {
        if (!lyricFacade.existLyric(m, loggedUserMb.getUser())) {
            this.originalLyric = webServiceController.lyricRESTResult(m);
            music = m;
            update = false;
        } else {
            try {
                this.selectLyric = lyricFacade.findLyric(m, loggedUserMb.getUser());
                this.originalLyric = webServiceController.lyricRESTResult(m);
                music = m;
                update = true;
            } catch (Exception ex) {
                Logger.getLogger(LyricController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void prepareEdit(Music m) throws Exception {
        if (!lyricFacade.existLyric(m, loggedUserMb.getUser())) {
            lyricWikiREST(m);
            update = false;
        } else {
            try {
                this.selectLyric = lyricFacade.findLyric(m, loggedUserMb.getUser());
                this.originalLyric = selectLyric.getTextLyric();
                music = m;
                update = true;
            } catch (Exception ex) {
                Logger.getLogger(LyricController.class.getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage("No original lyric available! ");
            }
        }
    }

    public void save() {
        if (update) {
            Lyric updatedLyric = selectLyric;
            updatedLyric.setTextLyric(originalLyric);
            lyricFacade.editLyric(updatedLyric);

        } else {
            lyric = new Lyric();
            lyric.setAppuser(loggedUserMb.getUser());
            lyric.setMusic(music);
            lyric.setTextLyric(originalLyric);
            lyricFacade.create(lyric);
        }
        update = false;
    }

}
