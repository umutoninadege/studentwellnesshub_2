package com.wellnesshub.wellness;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@ConditionalOnProperty(prefix = "app.data", name = "init", havingValue = "true", matchIfMissing = false)
public class WellnessDataInitializer implements CommandLineRunner {

    private final WellnessCategoryRepository wellnessCategoryRepository;
    private final WellnessResourceRepository wellnessResourceRepository;

    public WellnessDataInitializer(WellnessCategoryRepository wellnessCategoryRepository,
                                 WellnessResourceRepository wellnessResourceRepository) {
        this.wellnessCategoryRepository = wellnessCategoryRepository;
        this.wellnessResourceRepository = wellnessResourceRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (wellnessCategoryRepository.count() == 0) {
            initializeWellnessData();
        }
    }

    private void initializeWellnessData() {
        // Create Wellness Categories
        WellnessCategory mentalHealth = new WellnessCategory("Mental Health", "Resources for mental health and emotional wellbeing");
        WellnessCategory physicalHealth = new WellnessCategory("Physical Health", "Resources for physical fitness and health");
        WellnessCategory academicSupport = new WellnessCategory("Academic Support", "Resources for academic success and study skills");
        WellnessCategory careerGuidance = new WellnessCategory("Career Guidance", "Resources for career planning and development");
        WellnessCategory socialSkills = new WellnessCategory("Social Skills", "Resources for building relationships and social connections");
        WellnessCategory stressManagement = new WellnessCategory("Stress Management", "Resources for managing stress and anxiety");

        mentalHealth = wellnessCategoryRepository.save(mentalHealth);
        physicalHealth = wellnessCategoryRepository.save(physicalHealth);
        academicSupport = wellnessCategoryRepository.save(academicSupport);
        careerGuidance = wellnessCategoryRepository.save(careerGuidance);
        socialSkills = wellnessCategoryRepository.save(socialSkills);
        stressManagement = wellnessCategoryRepository.save(stressManagement);

        // Create Mental Health Resources
        WellnessResource meditation = new WellnessResource(
            "Daily Meditation Practice",
            "A comprehensive guide to daily meditation for mental clarity and stress relief",
            "AUDIO",
            "https://example.com/meditation-audio",
            20,
            mentalHealth
        );

        WellnessResource anxietyManagement = new WellnessResource(
            "Managing Anxiety in College",
            "Practical strategies for dealing with anxiety during your academic journey",
            "ARTICLE",
            "https://example.com/anxiety-article",
            15,
            mentalHealth
        );

        WellnessResource mindfulness = new WellnessResource(
            "Mindfulness for Students",
            "Learn mindfulness techniques to improve focus and reduce stress",
            "VIDEO",
            "https://example.com/mindfulness-video",
            30,
            mentalHealth
        );

        wellnessResourceRepository.save(meditation);
        wellnessResourceRepository.save(anxietyManagement);
        wellnessResourceRepository.save(mindfulness);

        // Create Physical Health Resources
        WellnessResource workoutRoutine = new WellnessResource(
            "Student Workout Routine",
            "A 30-minute workout routine designed for busy students",
            "VIDEO",
            "https://example.com/workout-video",
            30,
            physicalHealth
        );

        WellnessResource nutritionGuide = new WellnessResource(
            "Healthy Eating on Campus",
            "Tips for maintaining a healthy diet while living on campus",
            "ARTICLE",
            "https://example.com/nutrition-article",
            10,
            physicalHealth
        );

        WellnessResource sleepHygiene = new WellnessResource(
            "Sleep Hygiene for Students",
            "Learn how to improve your sleep quality for better academic performance",
            "AUDIO",
            "https://example.com/sleep-audio",
            25,
            physicalHealth
        );

        wellnessResourceRepository.save(workoutRoutine);
        wellnessResourceRepository.save(nutritionGuide);
        wellnessResourceRepository.save(sleepHygiene);

        // Create Academic Support Resources
        WellnessResource studyTechniques = new WellnessResource(
            "Effective Study Techniques",
            "Proven study methods to improve your academic performance",
            "ARTICLE",
            "https://example.com/study-article",
            20,
            academicSupport
        );

        WellnessResource timeManagement = new WellnessResource(
            "Time Management for Students",
            "Learn how to effectively manage your time and prioritize tasks",
            "VIDEO",
            "https://example.com/time-management-video",
            25,
            academicSupport
        );

        WellnessResource examPreparation = new WellnessResource(
            "Exam Preparation Strategies",
            "Comprehensive guide to preparing for exams and reducing test anxiety",
            "ARTICLE",
            "https://example.com/exam-prep-article",
            15,
            academicSupport
        );

        wellnessResourceRepository.save(studyTechniques);
        wellnessResourceRepository.save(timeManagement);
        wellnessResourceRepository.save(examPreparation);

        // Create Career Guidance Resources
        WellnessResource resumeBuilding = new WellnessResource(
            "Building Your Resume",
            "Step-by-step guide to creating a professional resume",
            "ARTICLE",
            "https://example.com/resume-article",
            30,
            careerGuidance
        );

        WellnessResource interviewSkills = new WellnessResource(
            "Interview Skills Workshop",
            "Learn how to ace job interviews with confidence",
            "VIDEO",
            "https://example.com/interview-video",
            45,
            careerGuidance
        );

        WellnessResource networking = new WellnessResource(
            "Professional Networking",
            "Tips for building professional relationships and networking",
            "ARTICLE",
            "https://example.com/networking-article",
            20,
            careerGuidance
        );

        wellnessResourceRepository.save(resumeBuilding);
        wellnessResourceRepository.save(interviewSkills);
        wellnessResourceRepository.save(networking);

        // Create Social Skills Resources
        WellnessResource communicationSkills = new WellnessResource(
            "Effective Communication",
            "Improve your communication skills for better relationships",
            "VIDEO",
            "https://example.com/communication-video",
            35,
            socialSkills
        );

        WellnessResource conflictResolution = new WellnessResource(
            "Conflict Resolution Techniques",
            "Learn how to handle conflicts constructively",
            "ARTICLE",
            "https://example.com/conflict-article",
            15,
            socialSkills
        );

        wellnessResourceRepository.save(communicationSkills);
        wellnessResourceRepository.save(conflictResolution);

        // Create Stress Management Resources
        WellnessResource breathingExercises = new WellnessResource(
            "Breathing Exercises for Stress Relief",
            "Guided breathing exercises to reduce stress and anxiety",
            "AUDIO",
            "https://example.com/breathing-audio",
            10,
            stressManagement
        );

        WellnessResource relaxationTechniques = new WellnessResource(
            "Progressive Muscle Relaxation",
            "Learn progressive muscle relaxation for deep stress relief",
            "AUDIO",
            "https://example.com/relaxation-audio",
            20,
            stressManagement
        );

        WellnessResource stressJournaling = new WellnessResource(
            "Stress Journaling Guide",
            "Use journaling as a tool to manage and understand your stress",
            "ARTICLE",
            "https://example.com/journaling-article",
            12,
            stressManagement
        );

        wellnessResourceRepository.save(breathingExercises);
        wellnessResourceRepository.save(relaxationTechniques);
        wellnessResourceRepository.save(stressJournaling);

        System.out.println("Wellness data initialized successfully!");
    }
}


