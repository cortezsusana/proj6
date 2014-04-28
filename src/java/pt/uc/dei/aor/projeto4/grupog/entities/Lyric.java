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
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Elsa
 */
@IdClass(LyricId.class)
@Entity
public class Lyric implements Serializable {

    private static final long serialVersionUID = -8933754952599439717L;
    @Id
    @Column(name = "userId_fk")
    private Integer user_id;

    @Id
    @Column(name = "music_fk")
    private Integer music_id;

    @NotNull
    @Basic(optional = false)
    @Size(min = 1)
    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String textLyric;

    @PrimaryKeyJoinColumn(name = "music_fk", referencedColumnName = "music_id")
    @ManyToOne
    private Music music;

    @PrimaryKeyJoinColumn(name = "user_fk", referencedColumnName = "user_id")
    @ManyToOne
    private AppUser appuser;

    public Lyric() {
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getMusic_id() {
        return music_id;
    }

    public void setMusic_id(Integer music_id) {
        this.music_id = music_id;
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
        return "pt.uc.dei.ar.proj4.grupog.entities.Lyric[ userplay_id=" + user_id + "music_id=" + music_id + " ]";
    }
}
