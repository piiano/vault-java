package com.piiano.vault.orm.encryption;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.piiano.vault.client.CryptoClient;
import com.piiano.vault.client.model.AccessReason;
import com.piiano.vault.client.model.DecryptParams;
import com.piiano.vault.client.model.EncryptParams;
import com.piiano.vault.client.openapi.ApiException;
import com.piiano.vault.client.openapi.model.*;
import lombok.Getter;

import java.util.Collections;
import java.util.List;

import static com.piiano.vault.client.VaultClient.getPvaultClient;

public class Encryptor {

	public static final String PREFIX_ENCRYPTED = "encrypted_";

	private final CryptoClient cryptoClient;

	private final EncryptionType encryptionType;

	@Getter
	private final String collectionName;

	@Getter
	private final String propertyName;

	public Encryptor(EncryptionType encryptionType, String collectionName, String propertyName) {

		this.cryptoClient = new CryptoClient(getPvaultClient());

		this.encryptionType = encryptionType;
		this.collectionName = collectionName;
		this.propertyName = propertyName;
	}

	public boolean isEncrypted(String propValue) {
		return propValue != null
				&& propValue.startsWith(PREFIX_ENCRYPTED);
	}

	public String encrypt(String propValue) throws ApiException {

		EncryptionRequest request = new EncryptionRequest()
				.type(this.encryptionType)
				._object(new InputObject().fields(ImmutableMap.of(this.propertyName, propValue)));

		List<EncryptedValue> encrypted = this.cryptoClient.encrypt(
				EncryptParams.builder()
						.collection(this.collectionName)
						.encryptionRequest(ImmutableList.of(request))
						.accessReason(AccessReason.AppFunctionality)
						.build()
		);

		if (encrypted != null && encrypted.size() == 1) {
			return PREFIX_ENCRYPTED + encrypted.get(0).getCiphertext();
		}
		return null;
	}

	public Object decrypt(String ciphertext) throws ApiException {

		if (ciphertext == null) {
			throw new ApiException("ciphertext must not be null");
		}

		if (isEncrypted(ciphertext)) {
			ciphertext = ciphertext.substring(PREFIX_ENCRYPTED.length());
		}

		DecryptionRequest request = new DecryptionRequest().encryptedObject(
				new EncryptedObjectInput().ciphertext(ciphertext)).props(ImmutableList.of(this.propertyName));

		List<DecryptedObject> decrypted = this.cryptoClient.decrypt(
				DecryptParams.builder()
						.collection(this.collectionName)
						.decryptionRequests(ImmutableList.of(request))
						.accessReason(AccessReason.AppFunctionality)
						.build()
		);

		if (decrypted != null && decrypted.size() == 1) {
			return decrypted.get(0).getFields().get(this.propertyName);
		}
		return null;
	}
}
