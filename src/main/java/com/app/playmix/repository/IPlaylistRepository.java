package com.app.playmix.repository;

import com.app.playmix.model.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPlaylistRepository extends JpaRepository<Playlist, Long> {

}
