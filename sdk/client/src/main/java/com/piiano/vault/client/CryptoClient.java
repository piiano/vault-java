package com.piiano.vault.client;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.piiano.vault.client.model.*;
import com.piiano.vault.client.openapi.ApiClient;
import com.piiano.vault.client.openapi.ApiException;
import com.piiano.vault.client.openapi.CryptoApi;
import com.piiano.vault.client.openapi.model.*;

import java.util.List;
import java.util.Set;

/**
 * Client for the Crypto API.
 */
public class CryptoClient {

    private final CryptoApi cryptoApi;

    private final DefaultParams defaultParams;

    public CryptoClient(ApiClient apiClient, DefaultParams defaultParams) {
        this.cryptoApi = new CryptoApi(apiClient);
        this.defaultParams = defaultParams;
    }

    public CryptoApi openapi() {
        return cryptoApi;
    }

    /**
     * Encrypt
     * Creates an encrypted blob.  You can encrypt:  1. Objects stored in Vault by providing their ID.  2. Fields not stored in Vault by providing a map of their properties and property values. However, fields must conform to the collection schema specified in the request.  3. Encrypted blob. This option enables you to create an encrypted blob with a subset of another encrypted object's properties or use a different encryption type or scope. To update the property values, use the [update encrypted](/api/operations/update-encrypted) operation.  :bulb: For brevity, this guide uses the term 'object' to refer to the content being encrypted.  If any request details are invalid, none of the objects are encrypted.  You can generate ciphertext for the encrypted object with a random initialization vector (randomized) or based on the collection name, input object, and scope (deterministic).  Using 'props', you can request that the encrypted object include a subset of the collection schema's properties.  However, if a property isn't present in the object or the property value is null, it is not included in the encrypted blob. Also, if you want to include built-in properties, you must explicitly include them in 'props'.  The role that performs this operation must have  the following capabilities:  - 'CapDataWriter' - 'CapDataReader' to encrypt objects stored in Vault, along with at least one allowing policy and no denying policies for the read operation for the collection and each property requested in the call.  See [identity and access management](/data-security/identity-and-access-management) for more information about how Vault uses capabilities to control access to operations and policies to control access to data.
     * @param encryptionRequest Details of the encryption request. (required)
     * @return EncryptedValue
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * {@code @http.response.details}
    <table summary="Response Details" border="1">
    <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
    <tr><td> 200 </td><td> The request is successful. </td><td>  -  </td></tr>
    <tr><td> 400 </td><td> The request is invalid. </td><td>  -  </td></tr>
    <tr><td> 401 </td><td> Authentication credentials are incorrect or missing. </td><td>  -  </td></tr>
    <tr><td> 403 </td><td> The caller doesn't have the required access rights. </td><td>  -  </td></tr>
    <tr><td> 404 </td><td> The collection or properties aren't found, or property values are invalid. </td><td>  -  </td></tr>
    <tr><td> 409 </td><td> A conflict occurs. </td><td>  -  </td></tr>
    <tr><td> 500 </td><td> An error occurs on the server. </td><td>  -  </td></tr>
    <tr><td> 503 </td><td> The service is unavailable. </td><td>  -  </td></tr>
    </table>
     */
    public EncryptedValue encrypt(EncryptionRequest encryptionRequest) throws ApiException {

        List<EncryptedValue> encryptedValues = this.cryptoApi.encrypt(
                this.defaultParams.getCollection(),
                this.defaultParams.getAccessReason().getReason(),
                ImmutableList.of(encryptionRequest),
                this.defaultParams.getExpirationSecs(),
                this.defaultParams.getAccessReason().getAdhocReason(),
                this.defaultParams.isReloadCache()
        );

        if (encryptedValues.size() == 0) {
            return null;
        }
        return encryptedValues.get(0);
    }

    /**
     * Encrypt
     * Creates an encrypted blob.  You can encrypt:  1. Objects stored in Vault by providing their ID.  2. Fields not stored in Vault by providing a map of their properties and property values. However, fields must conform to the collection schema specified in the request.  3. Encrypted blob. This option enables you to create an encrypted blob with a subset of another encrypted object's properties or use a different encryption type or scope. To update the property values, use the [update encrypted](/api/operations/update-encrypted) operation.  :bulb: For brevity, this guide uses the term 'object' to refer to the content being encrypted.  If any request details are invalid, none of the objects are encrypted.  You can generate ciphertext for the encrypted object with a random initialization vector (randomized) or based on the collection name, input object, and scope (deterministic).  Using 'props', you can request that the encrypted object include a subset of the collection schema's properties.  However, if a property isn't present in the object or the property value is null, it is not included in the encrypted blob. Also, if you want to include built-in properties, you must explicitly include them in 'props'.  The role that performs this operation must have  the following capabilities:  - 'CapDataWriter' - 'CapDataReader' to encrypt objects stored in Vault, along with at least one allowing policy and no denying policies for the read operation for the collection and each property requested in the call.  See [identity and access management](/data-security/identity-and-access-management) for more information about how Vault uses capabilities to control access to operations and policies to control access to data.
     * @param encryptionRequest Details of the encryption request. (required)
     * @param encryptParams Additional params for the request.
     * @return EncryptedValue
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * {@code @http.response.details}
    <table summary="Response Details" border="1">
    <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
    <tr><td> 200 </td><td> The request is successful. </td><td>  -  </td></tr>
    <tr><td> 400 </td><td> The request is invalid. </td><td>  -  </td></tr>
    <tr><td> 401 </td><td> Authentication credentials are incorrect or missing. </td><td>  -  </td></tr>
    <tr><td> 403 </td><td> The caller doesn't have the required access rights. </td><td>  -  </td></tr>
    <tr><td> 404 </td><td> The collection or properties aren't found, or property values are invalid. </td><td>  -  </td></tr>
    <tr><td> 409 </td><td> A conflict occurs. </td><td>  -  </td></tr>
    <tr><td> 500 </td><td> An error occurs on the server. </td><td>  -  </td></tr>
    <tr><td> 503 </td><td> The service is unavailable. </td><td>  -  </td></tr>
    </table>
     */
    public EncryptedValue encrypt(EncryptionRequest encryptionRequest, EncryptParams encryptParams) throws ApiException {

        AccessReason accessReason = encryptParams.getAccessReason() != null ? encryptParams.getAccessReason() : this.defaultParams.getAccessReason();

        List<EncryptedValue> encryptedValues = this.cryptoApi.encrypt(
                !Strings.isNullOrEmpty(encryptParams.getCollection()) ? encryptParams.getCollection() : this.defaultParams.getCollection(),
                accessReason.getReason(),
                ImmutableList.of(encryptionRequest),
                encryptParams.getExpirationSecs(),
                accessReason.getAdhocReason(),
                encryptParams.getReloadCache() != null ? encryptParams.getReloadCache() : this.defaultParams.isReloadCache()
        );

        if (encryptedValues.size() == 0) {
            return null;
        }
        return encryptedValues.get(0);
    }

    /**
     * Encrypt bulk
     * Creates bulk of encrypted blobs.  You can encrypt:  1. Objects stored in Vault by providing their ID.  2. Fields not stored in Vault by providing a map of their properties and property values. However, fields must conform to the collection schema specified in the request.  3. Encrypted blob. This option enables you to create an encrypted blob with a subset of another encrypted object's properties or use a different encryption type or scope. To update the property values, use the [update encrypted](/api/operations/update-encrypted) operation.  :bulb: For brevity, this guide uses the term 'object' to refer to the content being encrypted.  If any request details are invalid, none of the objects are encrypted.  You can generate ciphertext for the encrypted object with a random initialization vector (randomized) or based on the collection name, input object, and scope (deterministic).  Using 'props', you can request that the encrypted object include a subset of the collection schema's properties.  However, if a property isn't present in the object or the property value is null, it is not included in the encrypted blob. Also, if you want to include built-in properties, you must explicitly include them in 'props'.  The role that performs this operation must have  the following capabilities:  - 'CapDataWriter' - 'CapDataReader' to encrypt objects stored in Vault, along with at least one allowing policy and no denying policies for the read operation for the collection and each property requested in the call.  See [identity and access management](/data-security/identity-and-access-management) for more information about how Vault uses capabilities to control access to operations and policies to control access to data.
     * @param encryptionRequests Details of the encryption requests. (required)
     * @return List<EncryptedValue>
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * {@code @http.response.details}
    <table summary="Response Details" border="1">
    <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
    <tr><td> 200 </td><td> The request is successful. </td><td>  -  </td></tr>
    <tr><td> 400 </td><td> The request is invalid. </td><td>  -  </td></tr>
    <tr><td> 401 </td><td> Authentication credentials are incorrect or missing. </td><td>  -  </td></tr>
    <tr><td> 403 </td><td> The caller doesn't have the required access rights. </td><td>  -  </td></tr>
    <tr><td> 404 </td><td> The collection or properties aren't found, or property values are invalid. </td><td>  -  </td></tr>
    <tr><td> 409 </td><td> A conflict occurs. </td><td>  -  </td></tr>
    <tr><td> 500 </td><td> An error occurs on the server. </td><td>  -  </td></tr>
    <tr><td> 503 </td><td> The service is unavailable. </td><td>  -  </td></tr>
    </table>
     */
    public List<EncryptedValue> encryptBulk(List<EncryptionRequest> encryptionRequests) throws ApiException {

        return this.cryptoApi.encrypt(
                this.defaultParams.getCollection(),
                this.defaultParams.getAccessReason().getReason(),
                encryptionRequests,
                this.defaultParams.getExpirationSecs(),
                this.defaultParams.getAccessReason().getAdhocReason(),
                this.defaultParams.isReloadCache()
        );
    }

    /**
     * Encrypt bulk
     * Creates bulk of encrypted blobs.  You can encrypt:  1. Objects stored in Vault by providing their ID.  2. Fields not stored in Vault by providing a map of their properties and property values. However, fields must conform to the collection schema specified in the request.  3. Encrypted blob. This option enables you to create an encrypted blob with a subset of another encrypted object's properties or use a different encryption type or scope. To update the property values, use the [update encrypted](/api/operations/update-encrypted) operation.  :bulb: For brevity, this guide uses the term 'object' to refer to the content being encrypted.  If any request details are invalid, none of the objects are encrypted.  You can generate ciphertext for the encrypted object with a random initialization vector (randomized) or based on the collection name, input object, and scope (deterministic).  Using 'props', you can request that the encrypted object include a subset of the collection schema's properties.  However, if a property isn't present in the object or the property value is null, it is not included in the encrypted blob. Also, if you want to include built-in properties, you must explicitly include them in 'props'.  The role that performs this operation must have  the following capabilities:  - 'CapDataWriter' - 'CapDataReader' to encrypt objects stored in Vault, along with at least one allowing policy and no denying policies for the read operation for the collection and each property requested in the call.  See [identity and access management](/data-security/identity-and-access-management) for more information about how Vault uses capabilities to control access to operations and policies to control access to data.
     * @param encryptionRequests Details of the encryption request. (required)
     * @param encryptParams Additional params for the request.
     * @return List<EncryptedValue>
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * {@code @http.response.details}
    <table summary="Response Details" border="1">
    <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
    <tr><td> 200 </td><td> The request is successful. </td><td>  -  </td></tr>
    <tr><td> 400 </td><td> The request is invalid. </td><td>  -  </td></tr>
    <tr><td> 401 </td><td> Authentication credentials are incorrect or missing. </td><td>  -  </td></tr>
    <tr><td> 403 </td><td> The caller doesn't have the required access rights. </td><td>  -  </td></tr>
    <tr><td> 404 </td><td> The collection or properties aren't found, or property values are invalid. </td><td>  -  </td></tr>
    <tr><td> 409 </td><td> A conflict occurs. </td><td>  -  </td></tr>
    <tr><td> 500 </td><td> An error occurs on the server. </td><td>  -  </td></tr>
    <tr><td> 503 </td><td> The service is unavailable. </td><td>  -  </td></tr>
    </table>
     */
    public List<EncryptedValue> encryptBulk(List<EncryptionRequest> encryptionRequests, EncryptParams encryptParams) throws ApiException {

        AccessReason accessReason = encryptParams.getAccessReason() != null ? encryptParams.getAccessReason() : this.defaultParams.getAccessReason();

        return this.cryptoApi.encrypt(
                !Strings.isNullOrEmpty(encryptParams.getCollection()) ? encryptParams.getCollection() : this.defaultParams.getCollection(),
                accessReason.getReason(),
                encryptionRequests,
                encryptParams.getExpirationSecs(),
                accessReason.getAdhocReason(),
                encryptParams.getReloadCache() != null ? encryptParams.getReloadCache() : this.defaultParams.isReloadCache()
        );
    }

    /**
     * Decrypt
     * Decrypts the ciphertext of an encrypted blob.  To decrypt an encrypted blob, the request must include the scope used when the blob was encrypted. By default, only blobs considered active are decrypted. A blob is considered active when its metadata 'expiration' property is for the current date or a date in the future. If 'options' is set to 'archived', the blob is only decrypted if its metadata 'expiration' property is for a date in the past.  By default, all property values from an encrypted blob are returned. Use 'props' to return a subset of the encrypted property values.  If any request details are invalid, none of the blobs are decrypted.  The role that performs this operation must have the 'CapDataReader' capability. See [identity and access management](/data-security/identity-and-access-management) for more information about how Vault uses capabilities to control access to operations and policies to control access to data.
     * @param decryptionRequest Details of the decryption request. (required)
     * @param options Options for the operation. Options include: - 'archived' – whether to decrypt only archived objects. If not specified, decrypts only active objects. - 'include_metadata' - show the encrypted object metadata.  (optional)
     * @return DecryptedObject
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * {@code @http.response.details}
    <table summary="Response Details" border="1">
    <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
    <tr><td> 200 </td><td> The request is successful. </td><td>  -  </td></tr>
    <tr><td> 400 </td><td> The request is invalid. </td><td>  -  </td></tr>
    <tr><td> 401 </td><td> Authentication credentials are incorrect or missing. </td><td>  -  </td></tr>
    <tr><td> 403 </td><td> The caller doesn't have the required access rights. </td><td>  -  </td></tr>
    <tr><td> 404 </td><td> The collection or properties aren't found. </td><td>  -  </td></tr>
    <tr><td> 409 </td><td> A conflict occurs. </td><td>  -  </td></tr>
    <tr><td> 500 </td><td> An error occurs on the server. </td><td>  -  </td></tr>
    <tr><td> 503 </td><td> The service is unavailable. </td><td>  -  </td></tr>
    </table>
     */
    public DecryptedObject decrypt(DecryptionRequest decryptionRequest, Set<String> options) throws ApiException {

        List<DecryptedObject> decryptedObjects = this.cryptoApi.decrypt(
                this.defaultParams.getCollection(),
                this.defaultParams.getAccessReason().getReason(),
                ImmutableList.of(decryptionRequest),
                options,
                this.defaultParams.getAccessReason().getAdhocReason(),
                this.defaultParams.isReloadCache()
        );

        if (decryptedObjects.size() == 0) {
            return null;
        }
        return decryptedObjects.get(0);
    }

    /**
     * Decrypt
     * Decrypts the ciphertext of an encrypted blob.  To decrypt an encrypted blob, the request must include the scope used when the blob was encrypted. By default, only blobs considered active are decrypted. A blob is considered active when its metadata 'expiration' property is for the current date or a date in the future. If 'options' is set to 'archived', the blob is only decrypted if its metadata 'expiration' property is for a date in the past.  By default, all property values from an encrypted blob are returned. Use 'props' to return a subset of the encrypted property values.  If any request details are invalid, none of the blobs are decrypted.  The role that performs this operation must have the 'CapDataReader' capability. See [identity and access management](/data-security/identity-and-access-management) for more information about how Vault uses capabilities to control access to operations and policies to control access to data.
     * @param decryptionRequest Details of the decryption request. (required)
     * @param options Options for the operation. Options include: - 'archived' – whether to decrypt only archived objects. If not specified, decrypts only active objects. - 'include_metadata' - show the encrypted object metadata.  (optional)
     * @param decryptParams Additional params for the request.
     * @return DecryptedObject
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * {@code @http.response.details}
    <table summary="Response Details" border="1">
    <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
    <tr><td> 200 </td><td> The request is successful. </td><td>  -  </td></tr>
    <tr><td> 400 </td><td> The request is invalid. </td><td>  -  </td></tr>
    <tr><td> 401 </td><td> Authentication credentials are incorrect or missing. </td><td>  -  </td></tr>
    <tr><td> 403 </td><td> The caller doesn't have the required access rights. </td><td>  -  </td></tr>
    <tr><td> 404 </td><td> The collection or properties aren't found. </td><td>  -  </td></tr>
    <tr><td> 409 </td><td> A conflict occurs. </td><td>  -  </td></tr>
    <tr><td> 500 </td><td> An error occurs on the server. </td><td>  -  </td></tr>
    <tr><td> 503 </td><td> The service is unavailable. </td><td>  -  </td></tr>
    </table>
     */
    public DecryptedObject decrypt(DecryptionRequest decryptionRequest, Set<String> options, DecryptParams decryptParams) throws ApiException {

        AccessReason accessReason = decryptParams.getAccessReason() != null ? decryptParams.getAccessReason() : this.defaultParams.getAccessReason();

        List<DecryptedObject> decryptedObjects = this.cryptoApi.decrypt(
                !Strings.isNullOrEmpty(decryptParams.getCollection()) ? decryptParams.getCollection() : this.defaultParams.getCollection(),
                accessReason.getReason(),
                ImmutableList.of(decryptionRequest),
                options,
                accessReason.getAdhocReason(),
                decryptParams.getReloadCache() != null ? decryptParams.getReloadCache() : this.defaultParams.isReloadCache()
        );

        if (decryptedObjects.size() == 0) {
            return null;
        }
        return decryptedObjects.get(0);
    }

    /**
     * Decrypt bulk
     * Decrypts the ciphertext of an encrypted blob. Supports bulk operations.  To decrypt an encrypted blob, the request must include the scope used when the blob was encrypted. By default, only blobs considered active are decrypted. A blob is considered active when its metadata 'expiration' property is for the current date or a date in the future. If 'options' is set to 'archived', the blob is only decrypted if its metadata 'expiration' property is for a date in the past.  By default, all property values from an encrypted blob are returned. Use 'props' to return a subset of the encrypted property values.  If any request details are invalid, none of the blobs are decrypted.  The role that performs this operation must have the 'CapDataReader' capability. See [identity and access management](/data-security/identity-and-access-management) for more information about how Vault uses capabilities to control access to operations and policies to control access to data.
     * @param decryptionRequests Details of the decryption requests. (required)
     * @param options Options for the operation. Options include: - 'archived' – whether to decrypt only archived objects. If not specified, decrypts only active objects. - 'include_metadata' - show the encrypted object metadata.  (optional)
     * @return List<DecryptedObject>
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * {@code @http.response.details}
    <table summary="Response Details" border="1">
    <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
    <tr><td> 200 </td><td> The request is successful. </td><td>  -  </td></tr>
    <tr><td> 400 </td><td> The request is invalid. </td><td>  -  </td></tr>
    <tr><td> 401 </td><td> Authentication credentials are incorrect or missing. </td><td>  -  </td></tr>
    <tr><td> 403 </td><td> The caller doesn't have the required access rights. </td><td>  -  </td></tr>
    <tr><td> 404 </td><td> The collection or properties aren't found. </td><td>  -  </td></tr>
    <tr><td> 409 </td><td> A conflict occurs. </td><td>  -  </td></tr>
    <tr><td> 500 </td><td> An error occurs on the server. </td><td>  -  </td></tr>
    <tr><td> 503 </td><td> The service is unavailable. </td><td>  -  </td></tr>
    </table>
     */
    public List<DecryptedObject> decryptBulk(List<DecryptionRequest> decryptionRequests, Set<String> options) throws ApiException {

        return this.cryptoApi.decrypt(
                this.defaultParams.getCollection(),
                this.defaultParams.getAccessReason().getReason(),
                decryptionRequests,
                options,
                this.defaultParams.getAccessReason().getAdhocReason(),
                this.defaultParams.isReloadCache()
        );
    }

    /**
     * Decrypt bulk
     * Decrypts the ciphertext of an encrypted blob. Supports bulk operations.  To decrypt an encrypted blob, the request must include the scope used when the blob was encrypted. By default, only blobs considered active are decrypted. A blob is considered active when its metadata 'expiration' property is for the current date or a date in the future. If 'options' is set to 'archived', the blob is only decrypted if its metadata 'expiration' property is for a date in the past.  By default, all property values from an encrypted blob are returned. Use 'props' to return a subset of the encrypted property values.  If any request details are invalid, none of the blobs are decrypted.  The role that performs this operation must have the 'CapDataReader' capability. See [identity and access management](/data-security/identity-and-access-management) for more information about how Vault uses capabilities to control access to operations and policies to control access to data.
     * @param decryptionRequests Details of the decryption request. (required)
     * @param options Options for the operation. Options include: - 'archived' – whether to decrypt only archived objects. If not specified, decrypts only active objects. - 'include_metadata' - show the encrypted object metadata.  (optional)
     * @param decryptParams Additional params for the request.
     * @return List<DecryptedObject>
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * {@code @http.response.details}
    <table summary="Response Details" border="1">
    <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
    <tr><td> 200 </td><td> The request is successful. </td><td>  -  </td></tr>
    <tr><td> 400 </td><td> The request is invalid. </td><td>  -  </td></tr>
    <tr><td> 401 </td><td> Authentication credentials are incorrect or missing. </td><td>  -  </td></tr>
    <tr><td> 403 </td><td> The caller doesn't have the required access rights. </td><td>  -  </td></tr>
    <tr><td> 404 </td><td> The collection or properties aren't found. </td><td>  -  </td></tr>
    <tr><td> 409 </td><td> A conflict occurs. </td><td>  -  </td></tr>
    <tr><td> 500 </td><td> An error occurs on the server. </td><td>  -  </td></tr>
    <tr><td> 503 </td><td> The service is unavailable. </td><td>  -  </td></tr>
    </table>
     */
    public List<DecryptedObject> decryptBulk(List<DecryptionRequest> decryptionRequests, Set<String> options, DecryptParams decryptParams) throws ApiException {

        AccessReason accessReason = decryptParams.getAccessReason() != null ? decryptParams.getAccessReason() : this.defaultParams.getAccessReason();

        return this.cryptoApi.decrypt(
                !Strings.isNullOrEmpty(decryptParams.getCollection()) ? decryptParams.getCollection() : this.defaultParams.getCollection(),
                accessReason.getReason(),
                decryptionRequests,
                options,
                accessReason.getAdhocReason(),
                decryptParams.getReloadCache() != null ? decryptParams.getReloadCache() : this.defaultParams.isReloadCache()
        );
    }

    /**
     * Update encrypted blob and metadata
     * Creates an encrypted blob based on an encrypted blob.  The new encrypted blob can have different encryption type, scope, properties, or property values to the source blob. Any property values in the source blob not updated in the request are preserved in the new blob as long as they are included in &#x60;props&#x60;.  For example, if the source blob contains &#x60;first_name&#x60;, &#x60;last_name&#x60;, and &#x60;telephone_number&#x60; and &#x60;props&#x60; specifies &#x60;[first_name, last_name]&#x60;, then &#x60;telephone_number&#x60; isn't included in the new encrypted blob.  The request must include the scope used to encrypt the source blob.  If any request details are invalid, no encrypted blobs are created.  The role that performs this operation must have the &#x60;CapDataReader&#x60; and &#x60;CapDataWriter&#x60; capabilities. See [identity and access management](/data-security/identity-and-access-management) for more information about how Vault uses capabilities to control access to operations and policies to control access to data.
     * @param updateEncryptionRequest Details of the update encrypted object request. The request includes the encrypted object to update and the property values to update the encrypted object with.  (required)
     * @param options Options for the request. - &#x60;archived&#x60; - whether to update only archived encrypted objects. If not specified, update only active encrypted objects.  (optional)
     * @return EncryptedValue
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * {@code @http.response.details}
    <table summary="Response Details" border="1">
    <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
    <tr><td> 200 </td><td> The request is successful. </td><td>  -  </td></tr>
    <tr><td> 400 </td><td> The request is invalid. </td><td>  -  </td></tr>
    <tr><td> 401 </td><td> Authentication credentials are incorrect or missing. </td><td>  -  </td></tr>
    <tr><td> 403 </td><td> The caller doesn't have the required access rights. </td><td>  -  </td></tr>
    <tr><td> 404 </td><td> The collection or properties aren't found, or property values are invalid. </td><td>  -  </td></tr>
    <tr><td> 409 </td><td> A conflict occurs. </td><td>  -  </td></tr>
    <tr><td> 500 </td><td> An error occurs on the server. </td><td>  -  </td></tr>
    <tr><td> 503 </td><td> The service is unavailable. </td><td>  -  </td></tr>
    </table>
     */
    public EncryptedValue updateEncrypted(UpdateEncryptionRequest updateEncryptionRequest, Set<String> options) throws ApiException {

        List<EncryptedValue> encryptedValues = this.cryptoApi.updateEncrypted(
                this.defaultParams.getCollection(),
                this.defaultParams.getAccessReason().getReason(),
                ImmutableList.of(updateEncryptionRequest),
                options,
                this.defaultParams.getExpirationSecs(),
                this.defaultParams.getAccessReason().getAdhocReason(),
                this.defaultParams.isReloadCache()
        );

        if (encryptedValues.size() == 0) {
            return null;
        }
        return encryptedValues.get(0);
    }

    /**
     * Update encrypted blob and metadata
     * Creates an encrypted blob based on an encrypted blob.  The new encrypted blob can have different encryption type, scope, properties, or property values to the source blob. Any property values in the source blob not updated in the request are preserved in the new blob as long as they are included in &#x60;props&#x60;.  For example, if the source blob contains &#x60;first_name&#x60;, &#x60;last_name&#x60;, and &#x60;telephone_number&#x60; and &#x60;props&#x60; specifies &#x60;[first_name, last_name]&#x60;, then &#x60;telephone_number&#x60; isn't included in the new encrypted blob.  The request must include the scope used to encrypt the source blob.  If any request details are invalid, no encrypted blobs are created.  The role that performs this operation must have the &#x60;CapDataReader&#x60; and &#x60;CapDataWriter&#x60; capabilities. See [identity and access management](/data-security/identity-and-access-management) for more information about how Vault uses capabilities to control access to operations and policies to control access to data.
     * @param updateEncryptionRequest Details of the update encrypted object request. The request includes the encrypted object to update and the property values to update the encrypted object with.  (required)
     * @param options Options for the request. - &#x60;archived&#x60; - whether to update only archived encrypted objects. If not specified, update only active encrypted objects.  (optional)
     * @param updateEncryptedParams Additional params for the request.
     * @return EncryptedValue
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * {@code @http.response.details}
    <table summary="Response Details" border="1">
    <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
    <tr><td> 200 </td><td> The request is successful. </td><td>  -  </td></tr>
    <tr><td> 400 </td><td> The request is invalid. </td><td>  -  </td></tr>
    <tr><td> 401 </td><td> Authentication credentials are incorrect or missing. </td><td>  -  </td></tr>
    <tr><td> 403 </td><td> The caller doesn't have the required access rights. </td><td>  -  </td></tr>
    <tr><td> 404 </td><td> The collection or properties aren't found, or property values are invalid. </td><td>  -  </td></tr>
    <tr><td> 409 </td><td> A conflict occurs. </td><td>  -  </td></tr>
    <tr><td> 500 </td><td> An error occurs on the server. </td><td>  -  </td></tr>
    <tr><td> 503 </td><td> The service is unavailable. </td><td>  -  </td></tr>
    </table>
     */
    public EncryptedValue updateEncrypted(UpdateEncryptionRequest updateEncryptionRequest, Set<String> options, UpdateEncryptedParams updateEncryptedParams) throws ApiException {

        AccessReason accessReason = updateEncryptedParams.getAccessReason() != null ? updateEncryptedParams.getAccessReason() : this.defaultParams.getAccessReason();

        List<EncryptedValue> encryptedValues = this.cryptoApi.updateEncrypted(
                !Strings.isNullOrEmpty(updateEncryptedParams.getCollection()) ? updateEncryptedParams.getCollection() : this.defaultParams.getCollection(),
                accessReason.getReason(),
                ImmutableList.of(updateEncryptionRequest),
                options,
                updateEncryptedParams.getExpirationSecs(),
                accessReason.getAdhocReason(),
                updateEncryptedParams.getReloadCache() != null ? updateEncryptedParams.getReloadCache() : this.defaultParams.isReloadCache()
        );

        if (encryptedValues.size() == 0) {
            return null;
        }
        return encryptedValues.get(0);
    }

    /**
     * Update bulk encrypted blobs and metadata
     * Creates an encrypted blob based on an encrypted blob. Supports bulk operations.  The new encrypted blob can have different encryption type, scope, properties, or property values to the source blob. Any property values in the source blob not updated in the request are preserved in the new blob as long as they are included in &#x60;props&#x60;.  For example, if the source blob contains &#x60;first_name&#x60;, &#x60;last_name&#x60;, and &#x60;telephone_number&#x60; and &#x60;props&#x60; specifies &#x60;[first_name, last_name]&#x60;, then &#x60;telephone_number&#x60; isn't included in the new encrypted blob.  The request must include the scope used to encrypt the source blob.  If any request details are invalid, no encrypted blobs are created.  The role that performs this operation must have the &#x60;CapDataReader&#x60; and &#x60;CapDataWriter&#x60; capabilities. See [identity and access management](/data-security/identity-and-access-management) for more information about how Vault uses capabilities to control access to operations and policies to control access to data.
     * @param updateEncryptionRequests Details of the update encrypted object request. The request includes the encrypted object to update and the property values to update the encrypted object with.  (required)
     * @param options Options for the request. - &#x60;archived&#x60; - whether to update only archived encrypted objects. If not specified, update only active encrypted objects.  (optional)
     * @return List<EncryptedValue>
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * {@code @http.response.details}
    <table summary="Response Details" border="1">
    <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
    <tr><td> 200 </td><td> The request is successful. </td><td>  -  </td></tr>
    <tr><td> 400 </td><td> The request is invalid. </td><td>  -  </td></tr>
    <tr><td> 401 </td><td> Authentication credentials are incorrect or missing. </td><td>  -  </td></tr>
    <tr><td> 403 </td><td> The caller doesn't have the required access rights. </td><td>  -  </td></tr>
    <tr><td> 404 </td><td> The collection or properties aren't found, or property values are invalid. </td><td>  -  </td></tr>
    <tr><td> 409 </td><td> A conflict occurs. </td><td>  -  </td></tr>
    <tr><td> 500 </td><td> An error occurs on the server. </td><td>  -  </td></tr>
    <tr><td> 503 </td><td> The service is unavailable. </td><td>  -  </td></tr>
    </table>
     */
    public List<EncryptedValue> updateEncryptedBulk(List<UpdateEncryptionRequest> updateEncryptionRequests, Set<String> options) throws ApiException {

        return this.cryptoApi.updateEncrypted(
                this.defaultParams.getCollection(),
                this.defaultParams.getAccessReason().getReason(),
                updateEncryptionRequests,
                options,
                this.defaultParams.getExpirationSecs(),
                this.defaultParams.getAccessReason().getAdhocReason(),
                this.defaultParams.isReloadCache()
        );
    }

    /**
     * Update bulk encrypted blobs and metadata
     * Creates an encrypted blob based on an encrypted blob. Supports bulk operations.  The new encrypted blob can have different encryption type, scope, properties, or property values to the source blob. Any property values in the source blob not updated in the request are preserved in the new blob as long as they are included in &#x60;props&#x60;.  For example, if the source blob contains &#x60;first_name&#x60;, &#x60;last_name&#x60;, and &#x60;telephone_number&#x60; and &#x60;props&#x60; specifies &#x60;[first_name, last_name]&#x60;, then &#x60;telephone_number&#x60; isn't included in the new encrypted blob.  The request must include the scope used to encrypt the source blob.  If any request details are invalid, no encrypted blobs are created.  The role that performs this operation must have the &#x60;CapDataReader&#x60; and &#x60;CapDataWriter&#x60; capabilities. See [identity and access management](/data-security/identity-and-access-management) for more information about how Vault uses capabilities to control access to operations and policies to control access to data.
     * @param updateEncryptionRequests Details of the update encrypted object request. The request includes the encrypted object to update and the property values to update the encrypted object with.  (required)
     * @param options Options for the request. - &#x60;archived&#x60; - whether to update only archived encrypted objects. If not specified, update only active encrypted objects.  (optional)
     * @param updateEncryptedParams Additional params for the request.
     * @return List<EncryptedValue>
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * {@code @http.response.details}
    <table summary="Response Details" border="1">
    <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
    <tr><td> 200 </td><td> The request is successful. </td><td>  -  </td></tr>
    <tr><td> 400 </td><td> The request is invalid. </td><td>  -  </td></tr>
    <tr><td> 401 </td><td> Authentication credentials are incorrect or missing. </td><td>  -  </td></tr>
    <tr><td> 403 </td><td> The caller doesn't have the required access rights. </td><td>  -  </td></tr>
    <tr><td> 404 </td><td> The collection or properties aren't found, or property values are invalid. </td><td>  -  </td></tr>
    <tr><td> 409 </td><td> A conflict occurs. </td><td>  -  </td></tr>
    <tr><td> 500 </td><td> An error occurs on the server. </td><td>  -  </td></tr>
    <tr><td> 503 </td><td> The service is unavailable. </td><td>  -  </td></tr>
    </table>
     */
    public List<EncryptedValue> updateEncryptedBulk(List<UpdateEncryptionRequest> updateEncryptionRequests, Set<String> options, UpdateEncryptedParams updateEncryptedParams) throws ApiException {

        AccessReason accessReason = updateEncryptedParams.getAccessReason() != null ? updateEncryptedParams.getAccessReason() : this.defaultParams.getAccessReason();

        return this.cryptoApi.updateEncrypted(
                !Strings.isNullOrEmpty(updateEncryptedParams.getCollection()) ? updateEncryptedParams.getCollection() : this.defaultParams.getCollection(),
                accessReason.getReason(),
                updateEncryptionRequests,
                options,
                updateEncryptedParams.getExpirationSecs(),
                accessReason.getAdhocReason(),
                updateEncryptedParams.getReloadCache() != null ? updateEncryptedParams.getReloadCache() : this.defaultParams.isReloadCache()
        );
    }

    /**
     * Hash
     * Creates a deterministic hash based on an object's property values, collection, and scope.  This operation is similar to using the [tokenize](/api/operations/tokenize) operation for a token of type &#x60;deterministic&#x60;. The hash value is identical to the token ID generated for the same combination of collection, object, property values, and scope. However, unlike the token, this hash is not stored in Vault's storage and, as such, cannot be detokenized, searched, or invalidated.
     * @param hashObjectRequest Details of the hashing request. (required)
     * @return TokenValue
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * {@code @http.response.details}
    <table summary="Response Details" border="1">
    <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
    <tr><td> 200 </td><td> The request is successful. </td><td>  -  </td></tr>
    <tr><td> 400 </td><td> The request is invalid. </td><td>  -  </td></tr>
    <tr><td> 401 </td><td> Authentication credentials are incorrect or missing. </td><td>  -  </td></tr>
    <tr><td> 403 </td><td> The caller doesn't have the required access rights. </td><td>  -  </td></tr>
    <tr><td> 404 </td><td> The collection, objects, or properties aren't found or are missing. </td><td>  -  </td></tr>
    <tr><td> 409 </td><td> A conflict occurs. </td><td>  -  </td></tr>
    <tr><td> 500 </td><td> An error occurs on the server. </td><td>  -  </td></tr>
    <tr><td> 503 </td><td> The service is unavailable. </td><td>  -  </td></tr>
    </table>
     */
    public TokenValue hash(HashObjectRequest hashObjectRequest) throws ApiException {

        List<TokenValue> tokenValues = this.cryptoApi.hashObjects(
                this.defaultParams.getCollection(),
                this.defaultParams.getAccessReason().getReason(),
                ImmutableList.of(hashObjectRequest),
                this.defaultParams.getAccessReason().getAdhocReason(),
                this.defaultParams.isReloadCache()
        );

        if (tokenValues.size() == 0) {
            return null;
        }
        return tokenValues.get(0);
    }

    /**
     * Hash
     * Creates a deterministic hash based on an object's property values, collection, and scope. Supports bulk operations.  This operation is similar to using the [tokenize](/api/operations/tokenize) operation for a token of type &#x60;deterministic&#x60;. The hash value is identical to the token ID generated for the same combination of collection, object, property values, and scope. However, unlike the token, this hash is not stored in Vault's storage and, as such, cannot be detokenized, searched, or invalidated.
     * @param hashObjectRequest Details of the hashing request. (required)
     * @param hashParams Additional params for the request.
     * @return TokenValue
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * {@code @http.response.details}
    <table summary="Response Details" border="1">
    <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
    <tr><td> 200 </td><td> The request is successful. </td><td>  -  </td></tr>
    <tr><td> 400 </td><td> The request is invalid. </td><td>  -  </td></tr>
    <tr><td> 401 </td><td> Authentication credentials are incorrect or missing. </td><td>  -  </td></tr>
    <tr><td> 403 </td><td> The caller doesn't have the required access rights. </td><td>  -  </td></tr>
    <tr><td> 404 </td><td> The collection, objects, or properties aren't found or are missing. </td><td>  -  </td></tr>
    <tr><td> 409 </td><td> A conflict occurs. </td><td>  -  </td></tr>
    <tr><td> 500 </td><td> An error occurs on the server. </td><td>  -  </td></tr>
    <tr><td> 503 </td><td> The service is unavailable. </td><td>  -  </td></tr>
    </table>
     */
    public TokenValue hash(HashObjectRequest hashObjectRequest, HashParams hashParams) throws ApiException {

        AccessReason accessReason = hashParams.getAccessReason() != null ? hashParams.getAccessReason() : this.defaultParams.getAccessReason();

        List<TokenValue> tokenValues = this.cryptoApi.hashObjects(
                !Strings.isNullOrEmpty(hashParams.getCollection()) ? hashParams.getCollection() : this.defaultParams.getCollection(),
                accessReason.getReason(),
                ImmutableList.of(hashObjectRequest),
                accessReason.getAdhocReason(),
                hashParams.getReloadCache() != null ? hashParams.getReloadCache() : this.defaultParams.isReloadCache()
        );

        if (tokenValues.size() == 0) {
            return null;
        }
        return tokenValues.get(0);
    }

    /**
     * Hash bulk
     * Creates a deterministic hash based on an object's property values, collection, and scope. Supports bulk operations.  This operation is similar to using the [tokenize](/api/operations/tokenize) operation for a token of type &#x60;deterministic&#x60;. The hash value is identical to the token ID generated for the same combination of collection, object, property values, and scope. However, unlike the token, this hash is not stored in Vault's storage and, as such, cannot be detokenized, searched, or invalidated.
     * @param hashObjectRequests Details of the hashing request. (required)
     * @return List<TokenValue>
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * {@code @http.response.details}
    <table summary="Response Details" border="1">
    <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
    <tr><td> 200 </td><td> The request is successful. </td><td>  -  </td></tr>
    <tr><td> 400 </td><td> The request is invalid. </td><td>  -  </td></tr>
    <tr><td> 401 </td><td> Authentication credentials are incorrect or missing. </td><td>  -  </td></tr>
    <tr><td> 403 </td><td> The caller doesn't have the required access rights. </td><td>  -  </td></tr>
    <tr><td> 404 </td><td> The collection, objects, or properties aren't found or are missing. </td><td>  -  </td></tr>
    <tr><td> 409 </td><td> A conflict occurs. </td><td>  -  </td></tr>
    <tr><td> 500 </td><td> An error occurs on the server. </td><td>  -  </td></tr>
    <tr><td> 503 </td><td> The service is unavailable. </td><td>  -  </td></tr>
    </table>
     */
    public List<TokenValue> hashBulk(List<HashObjectRequest> hashObjectRequests) throws ApiException {

        return this.cryptoApi.hashObjects(
                this.defaultParams.getCollection(),
                this.defaultParams.getAccessReason().getReason(),
                hashObjectRequests,
                this.defaultParams.getAccessReason().getAdhocReason(),
                this.defaultParams.isReloadCache()
        );
    }

    /**
     * Hash bulk
     * Creates a deterministic hash based on an object's property values, collection, and scope. Supports bulk operations.  This operation is similar to using the [tokenize](/api/operations/tokenize) operation for a token of type &#x60;deterministic&#x60;. The hash value is identical to the token ID generated for the same combination of collection, object, property values, and scope. However, unlike the token, this hash is not stored in Vault's storage and, as such, cannot be detokenized, searched, or invalidated.
     * @param hashObjectRequests Details of the hashing request. (required)
     * @param hashParams Additional params for the request.
     * @return List<TokenValue>
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * {@code @http.response.details}
    <table summary="Response Details" border="1">
    <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
    <tr><td> 200 </td><td> The request is successful. </td><td>  -  </td></tr>
    <tr><td> 400 </td><td> The request is invalid. </td><td>  -  </td></tr>
    <tr><td> 401 </td><td> Authentication credentials are incorrect or missing. </td><td>  -  </td></tr>
    <tr><td> 403 </td><td> The caller doesn't have the required access rights. </td><td>  -  </td></tr>
    <tr><td> 404 </td><td> The collection, objects, or properties aren't found or are missing. </td><td>  -  </td></tr>
    <tr><td> 409 </td><td> A conflict occurs. </td><td>  -  </td></tr>
    <tr><td> 500 </td><td> An error occurs on the server. </td><td>  -  </td></tr>
    <tr><td> 503 </td><td> The service is unavailable. </td><td>  -  </td></tr>
    </table>
     */
    public List<TokenValue> hashBulk(List<HashObjectRequest> hashObjectRequests, HashParams hashParams) throws ApiException {

        AccessReason accessReason = hashParams.getAccessReason() != null ? hashParams.getAccessReason() : this.defaultParams.getAccessReason();

        return this.cryptoApi.hashObjects(
                !Strings.isNullOrEmpty(hashParams.getCollection()) ? hashParams.getCollection() : this.defaultParams.getCollection(),
                accessReason.getReason(),
                hashObjectRequests,
                accessReason.getAdhocReason(),
                hashParams.getReloadCache() != null ? hashParams.getReloadCache() : this.defaultParams.isReloadCache()
        );
    }
}
