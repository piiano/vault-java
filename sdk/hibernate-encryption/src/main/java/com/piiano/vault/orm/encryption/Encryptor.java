package com.piiano.vault.orm.encryption;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.piiano.vault.client.CryptoClient;
import com.piiano.vault.client.model.AccessReason;
import com.piiano.vault.client.model.DefaultParams;
import com.piiano.vault.client.openapi.ApiException;
import com.piiano.vault.client.openapi.model.*;
import lombok.Getter;

import java.util.Collections;

import static com.piiano.vault.client.VaultClient.getPvaultClient;

public class Encryptor {

	public static final String PREFIX_ENCRYPTED = "encrypted_";

	private final CryptoClient cryptoClient;

	private final EncryptionType encryptionType;

	@Getter
	private final String propertyName;

	public Encryptor(EncryptionType encryptionType, String collection, String propertyName) {

		this.cryptoClient = new CryptoClient(getPvaultClient(), DefaultParams.builder()
				.collection(collection)
				.accessReason(AccessReason.AppFunctionality).build());

		this.encryptionType = encryptionType;
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

		EncryptedValue encrypted = this.cryptoClient.encrypt(request);

		if (encrypted != null) {
			return PREFIX_ENCRYPTED + encrypted.getCiphertext();
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

		DecryptedObject decrypted = this.cryptoClient.decrypt(request, Collections.emptySet());

		if (decrypted != null) {
			return decrypted.getFields().get(this.propertyName);
		}
		return null;
	}
}
