package com.chat.room_service.infrastructure.adapter.out.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.chat.room_service.domain.model.RoomStatus;

@Repository
public interface RoomJpaRepository extends JpaRepository<RoomEntity, UUID> {
    Optional<RoomEntity> findByName(String name);
    List<RoomEntity> findByStatus(RoomStatus status);
    Optional<RoomEntity> findFirstByStatusOrderByCreatedAtAsc(RoomStatus status);
    @Query("SELECT r FROM RoomEntity r JOIN r.members m WHERE m.userId = :userId")
    Optional<RoomEntity> findByMemberUserId(@Param("userId") UUID userId);
    boolean existsByName(String name);
}
