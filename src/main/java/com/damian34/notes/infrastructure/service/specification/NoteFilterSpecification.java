package com.damian34.notes.infrastructure.service.specification;

import com.damian34.notes.domain.dto.NoteFilterDto;
import com.damian34.notes.infrastructure.db.NoteEntity;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.criteria.Predicate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NoteFilterSpecification {
    
    public static Specification<NoteEntity> filter(NoteFilterDto filterDto) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filterDto.id() != null) {
                predicates.add(criteriaBuilder.equal(root.get("id"), filterDto.id()));
            }

            if (StringUtils.isNotBlank(filterDto.name())) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("name")),
                        "%" + filterDto.name().toLowerCase() + "%"
                ));
            }

            if (StringUtils.isNotBlank(filterDto.content())) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("content")),
                        "%" + filterDto.content().toLowerCase() + "%"
                ));
            }

            return predicates.isEmpty() ? criteriaBuilder.conjunction() : criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
} 