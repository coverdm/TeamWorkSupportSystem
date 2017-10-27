package com.matuszak.projects.room.repository;

import com.matuszak.projects.room.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long>{
}
