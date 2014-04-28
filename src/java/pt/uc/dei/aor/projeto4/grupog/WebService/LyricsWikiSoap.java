/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.uc.dei.aor.projeto4.grupog.WebService;

import com.LyricWiki.LyricWikiPortType_Stub;
import com.LyricWiki.LyricWiki_Impl;
import com.LyricWiki.LyricsResult;
import java.rmi.RemoteException;

/**
 *
 * @author Elsa
 */
public class LyricsWikiSoap {

    public String songLyric(String artist, String song) throws RemoteException {
        LyricWikiPortType_Stub lw = createProxy();
        LyricsResult lr = lw.getSong(artist, song);
        return lr.getLyrics();
    }

    private static LyricWikiPortType_Stub createProxy() {
        return (LyricWikiPortType_Stub) (new LyricWiki_Impl().getLyricWikiPort());
    }

    public boolean checkSongExists(String artist, String song) throws RemoteException {
        LyricWikiPortType_Stub lwpts = createProxy();
        return lwpts.checkSongExists(artist, song);
    }
}
