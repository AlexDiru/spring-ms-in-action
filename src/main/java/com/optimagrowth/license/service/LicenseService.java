package com.optimagrowth.license.service;

import com.optimagrowth.license.model.License;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Random;

@Service
public class LicenseService {

    @Autowired
    MessageSource messages;

    public License getLicense(String licenseId, String organisationId) {
        var license = new License();
        license.setId(new Random().nextInt(1000));
        license.setLicenseId(licenseId);
        license.setOrganisatonId(organisationId);
        license.setDescription("Software product");
        license.setProductName("Ostock");
        license.setLicenseType("full");
        return license;
    }

    public String createLicense(License license, String organisationId, Locale locale) {
        String responseMessage = null;
        if (license != null) {
            license.setOrganisatonId(organisationId);
            responseMessage = String.format(messages.getMessage("license.create.message", null, locale), license);
        }
        return responseMessage;
    }

    public String updateLicense(License license, String organisationId, Locale locale) {
        String responseMessage = null;
        if (license != null) {
            license.setOrganisatonId(organisationId);
            responseMessage = String.format(messages.getMessage("license.update.message", null, locale), license);
        }
        return responseMessage;
    }

    public String deleteLicense(String licenseId, String organisationId, Locale locale) {
        String responseMessage = String.format(messages.getMessage("license.delete.message", null, locale), licenseId, organisationId);
        return responseMessage;
    }
}
