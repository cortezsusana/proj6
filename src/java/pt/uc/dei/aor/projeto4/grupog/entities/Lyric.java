/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.uc.dei.aor.projeto4.grupog.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Elsa
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Lyric.findLyricByMusic&User", query = "SELECT l FROM Lyric l WHERE l.music = :music and l.appuser = :appuser"),
    @NamedQuery(name = "Lyric.existLyric", query = "SELECT COUNT(l) FROM Lyric l WHERE l.music = :music and l.appuser = :appuser")
})
public class Lyric implements Serializable {

    private static final long serialVersionUID = -8933754952599439717L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long lyric_id;

    @NotNull
    @Basic(optional = false)
    @Size(min = 1)
    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String textLyric;

    @ManyToOne
    private Music music;

    @ManyToOne
    private AppUser appuser;

    public Lyric() {
    }

    public Long getLyric_id() {
        return lyric_id;
    }

    public void setLyric_id(Long lyric_id) {
        this.lyric_id = lyric_id;
    }

    public String getTextLyric() {
        return textLyric;
    }

    public void setTextLyric(String textLyric) {
        this.textLyric = textLyric;
    }

    public Music getMusic() {
        return music;
    }

    public void setMusic(Music music) {
        this.music = music;
    }

    public AppUser getAppuser() {
        return appuser;
    }

    public void setAppuser(AppUser appuser) {
        this.appuser = appuser;
    }

    @Override
    public String toString() {
        return "Lyric{" + "lyric_id=" + lyric_id + ", textLyric=" + textLyric + ", music=" + music + ", appuser=" + appuser + '}';
    }

}
