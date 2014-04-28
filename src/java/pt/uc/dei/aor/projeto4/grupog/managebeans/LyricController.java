/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pt.uc.dei.aor.projeto4.grupog.managebeans;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import pt.uc.dei.aor.projeto4.grupog.ejbs.LyricFacade;
import pt.uc.dei.aor.projeto4.grupog.entities.Lyric;

/**
 *
 * @author Elsa Susana
 */
@Named(value = "lyricController")
@ViewScoped
public class LyricController {

    @Inject
    private LyricFacade lyricFacade;
    private Lyric lyric;

    /**
     * Creates a new instance of LyricController
     */
    public LyricController() {
    }

    public void init() {
        this.lyric = new Lyric();
    }

    public void prepareEdit() {

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

}
