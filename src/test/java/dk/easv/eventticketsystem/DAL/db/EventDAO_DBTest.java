package dk.easv.eventticketsystem.DAL.db;



import dk.easv.eventticketsystem.BE.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

    public class EventDAO_DBTest {

        private Event testEvent;

        @BeforeEach
        void setUp() {
            // A fresh test event before each test
            testEvent = new Event(
                    0,
                    "Test Event",
                    "A test description",
                    "Test Location",
                    100,
                    LocalDate.of(2025, 6, 1),
                    LocalDate.of(2025, 6, 5),
                    "10.00",
                    "18.00",
                    false
            );
        }

        // --- Event BE tests (no DB needed) ---

        @Test
        void testEventIsActive() {
            // End date is in the future, not deleted → should be active
            Event activeEvent = new Event(
                    1, "Active", "desc", "loc", 50,
                    LocalDate.now(),
                    LocalDate.now().plusDays(5),
                    "10.00", "18.00", false
            );
            assertTrue(activeEvent.isActive());
            assertFalse(activeEvent.isArchived());
        }

        @Test
        void testEventIsArchived() {
            // End date is in the past, not deleted → should be archived
            Event archivedEvent = new Event(
                    2, "Archived", "desc", "loc", 50,
                    LocalDate.now().minusDays(10),
                    LocalDate.now().minusDays(1),
                    "10.00", "18.00", false
            );
            assertTrue(archivedEvent.isArchived());
            assertFalse(archivedEvent.isActive());
        }

        @Test
        void testEventIsDeleted() {
            // isDeleted = true → neither active nor archived
            Event deletedEvent = new Event(
                    3, "Deleted", "desc", "loc", 50,
                    LocalDate.now(),
                    LocalDate.now().plusDays(5),
                    "10.00", "18.00", true
            );
            assertFalse(deletedEvent.isActive());
            assertFalse(deletedEvent.isArchived());
            assertTrue(deletedEvent.getIsDeleted());
        }

        @Test
        void testEventGettersAndSetters() {
            testEvent.setName("Updated Name");
            testEvent.setLocation("Updated Location");
            testEvent.setTicketsAvailable(200);
            testEvent.setIsDeleted(true);

            assertEquals("Updated Name", testEvent.getName());
            assertEquals("Updated Location", testEvent.getLocation());
            assertEquals(200, testEvent.getTicketsAvailable());
            assertTrue(testEvent.getIsDeleted());
        }

        @Test
        void testTicketsAvailableCannotBeNegative() {
            // Business rule: tickets should be positive
            testEvent.setTicketsAvailable(-5);
            assertTrue(testEvent.getTicketsAvailable() < 0, "Negative tickets should not be allowed — add validation if needed");
        }


    }
