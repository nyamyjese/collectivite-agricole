package com.example.collectivite.config;

import com.example.collectivite.controller.CollectivityController;
import com.example.collectivite.controller.MemberController;
import com.example.collectivite.repository.*;
import com.example.collectivite.service.CollectivityService;
import com.example.collectivite.service.MemberAdmissionService;
import com.example.collectivite.validator.AdmissionValidator;
import com.example.collectivite.validator.CollectivityCreationValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public DBConnection dbConnection() {
        return new DBConnection();
    }

    @Bean
    public MemberRepository memberRepository(DBConnection db) {
        return new MemberRepository(db);
    }

    @Bean
    public SponsorshipRepository sponsorshipRepository(DBConnection db) {
        return new SponsorshipRepository(db);
    }

    @Bean
    public MembershipRepository membershipRepository(DBConnection db) {
        return new MembershipRepository(db);
    }

    @Bean
    public PaymentRepository paymentRepository(DBConnection db) {
        return new PaymentRepository(db);
    }

    @Bean
    public CollectivityRepository collectivityRepository(DBConnection db) {
        return new CollectivityRepository(db);
    }

    @Bean
    public CollectivityCreationValidator collectivityCreationValidator(
            MemberRepository memberRepo,
            MembershipRepository membershipRepo,
            CollectivityRepository collectivityRepo) {
        return new CollectivityCreationValidator(memberRepo, membershipRepo, collectivityRepo);
    }

    @Bean
    public AdmissionValidator admissionValidator(
            MemberRepository memberRepo,
            MembershipRepository membershipRepo,
            CollectivityRepository collectivityRepo) {
        return new AdmissionValidator(memberRepo, membershipRepo, collectivityRepo);
    }

    @Bean
    public CollectivityService collectivityService(
            CollectivityRepository collectivityRepo,
            MembershipRepository membershipRepo,
            DBConnection db,
            CollectivityCreationValidator validator) {
        return new CollectivityService(collectivityRepo, membershipRepo, db, validator);
    }

    @Bean
    public MemberAdmissionService memberAdmissionService(
            MemberRepository memberRepo,
            SponsorshipRepository sponsorRepo,
            MembershipRepository membershipRepo,
            PaymentRepository paymentRepo,
            CollectivityRepository collectivityRepo,
            DBConnection db,
            AdmissionValidator validator) {
        return new MemberAdmissionService(memberRepo, sponsorRepo, membershipRepo,
                paymentRepo, collectivityRepo, db, validator);
    }

    @Bean
    public CollectivityController collectivityController(CollectivityService service) {
        return new CollectivityController(service);
    }

    @Bean
    public MemberController memberController(MemberAdmissionService service) {
        return new MemberController(service);
    }
}