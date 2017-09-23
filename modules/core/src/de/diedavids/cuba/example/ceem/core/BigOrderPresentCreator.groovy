package de.diedavids.cuba.example.ceem.core

import com.haulmont.cuba.core.global.DataManager
import com.haulmont.cuba.core.global.Metadata
import com.haulmont.cuba.security.app.Authentication
import de.diedavids.cuba.example.ceem.entity.Present

import javax.inject.Inject

class BigOrderPresentCreator {


    @Inject
    Authentication authentication

    @Inject
    Metadata metadata

    @Inject
    DataManager dataManager


    void receive(String message) {


        authentication.begin()

        createPresentFromMessage(message)

        authentication.end()

    }

    private Present createPresentFromMessage(String message) {
        def (String customerName, Integer amount) = parseMessageDetails(message)

        if (amount > 100) {
            Present present = metadata.create(Present)
            present.customerName = customerName
            present.amount = amount

            dataManager.commit(present)
        }
    }

    private List parseMessageDetails(String message) {
        def messageParts = message.split(";")
        def customerName = messageParts[0]
        def amount = Integer.parseInt(messageParts[1])
        [customerName, amount]
    }
}
