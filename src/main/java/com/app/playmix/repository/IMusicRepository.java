package com.app.playmix.repository;

import com.app.playmix.model.Music;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMusicRepository extends JpaRepository<Music, Long> {
    
}
