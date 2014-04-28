/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.uc.dei.aor.projeto4.grupog.entities;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Elsa
 */
public class LyricId implements Serializable {

    private static final long serialVersionUID = 764973972649006552L;

    private Integer user_id;

    private Integer music_id;

    public LyricId() {
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += Objects.hashCode(this.user_id);
        hash += Objects.hashCode(this.music_id);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LyricId)) {
            return false;
        }
        LyricId other = (LyricId) object;
        if (!Objects.equals(this.user_id, other.user_id)) {
            return false;
        }
        if (!Objects.equals(this.music_id, other.music_id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pt.uc.dei.ar.proj4.grupog.entities.Lyric[ userplay_id=" + user_id + "music_id=" + music_id + " ]";
    }
}
