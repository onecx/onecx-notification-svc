package org.tkit.onecx.notification.domain.services;

import jakarta.inject.Inject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.tkit.onecx.notification.domain.daos.NotificationDAO;
import org.tkit.quarkus.jpa.exceptions.DAOException;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class NotificationSchedulerServiceExceptionTest {

    @Inject
    NotificationSchedulerService service;

    @InjectMock
    NotificationDAO dao;

    @BeforeEach
    void beforeAll() {
        Mockito.when(dao.findAllNotDelivered())
                .thenThrow(new DAOException(NotificationDAO.ErrorKeys.ERROR_FIND_ALL_NOT_DELIVERED,
                        new RuntimeException("Test technical error exception")));

    }

    @Test
    void testDaoException() {
        var exc = Assertions.assertThrows(DAOException.class, () -> {
            service.republishPersistedNotifications();
        });
        Assertions.assertEquals(NotificationDAO.ErrorKeys.ERROR_FIND_ALL_NOT_DELIVERED, exc.key);
    }

}
