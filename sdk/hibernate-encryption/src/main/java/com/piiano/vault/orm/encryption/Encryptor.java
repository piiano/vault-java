package com.piiano.vault.orm.encryption;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.piiano.vault.client.CryptoClient;
import com.piiano.vault.client.model.AccessReason;
import com.piiano.vault.client.model.DecryptParams;
import com.piiano.vault.client.model.DefaultParams;
import com.piiano.vault.client.model.EncryptParams;
import com.piiano.vault.client.openapi.ApiException;
import com.piiano.vault.client.openapi.model.*;

import java.util.Collections;

import static com.piiano.vault.client.VaultClient.getPvaultClient;

public class Encryptor {

	public static final String PREFIX_ENCRYPTED = "encrypted_";

	private final CryptoClient cryptoClient;

	public Encryptor() {
		cryptoClient = new CryptoClient(getPvaultClient(), DefaultParams.builder()
				.accessReason(AccessReason.AppFunctionality).build());
	}

	public boolean isEncrypted(String propValue) {
		return propValue.startsWith(PREFIX_ENCRYPTED);
	}

	public String encrypt(EncryptionType encryptionType, String collectionName, String propertyName, String propValue) throws ApiException {

		EncryptionRequest request = new EncryptionRequest()
				.type(encryptionType)
				._object(new InputObject().fields(ImmutableMap.of(propertyName, propValue)));

		EncryptedValue encrypted = cryptoClient.encrypt(request, EncryptParams.builder().collection(collectionName).build());

		if (encrypted != null) {
			return PREFIX_ENCRYPTED + encrypted.getCiphertext();
		}
		return null;
	}

	public Object decrypt(String collectionName, String propertyName, String ciphertext) throws ApiException {

		if (isEncrypted(ciphertext)) {
			ciphertext = ciphertext.substring(PREFIX_ENCRYPTED.length());
		}

		DecryptionRequest request = new DecryptionRequest().encryptedObject(
				new EncryptedObjectInput().ciphertext(ciphertext)).props(ImmutableList.of(propertyName));

		DecryptedObject decrypted = cryptoClient.decrypt(request, Collections.emptySet(),
				DecryptParams.builder().collection(collectionName).build());

		if (decrypted != null) {
			return decrypted.getFields().get(propertyName);
		}
		return null;
	}
}
