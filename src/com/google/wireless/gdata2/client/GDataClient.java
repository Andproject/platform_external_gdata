// Copyright 2007 The Android Open Source Project

package com.google.wireless.gdata2.client;

import com.google.wireless.gdata2.serializer.GDataSerializer;

import java.io.IOException;
import java.io.InputStream;

/**
 * Interface for interacting with a GData server.  Specific platforms can
 * provide their own implementations using the available networking and HTTP
 * stack for that platform.
 */
public interface GDataClient {

    /**
     * Closes this GDataClient, cleaning up any resources, persistent connections, etc.,
     * it may have.
     */
    void close();

    /**
     * URI encodes the supplied uri (using UTF-8).
     * @param uri The uri that should be encoded.
     * @return The encoded URI.
     */
    // TODO: get rid of this, if we write our own URI encoding library.
    String encodeUri(String uri);

    /**
     * Creates a new QueryParams that should be used to restrict the feed
     * contents that are fetched.
     * @return A new QueryParams.
     */
    // TODO: get rid of this, if we write a generic QueryParams that can encode
    // querystring params/values.
    QueryParams createQueryParams();

    /**
     * Connects to a GData server (specified by the feedUrl) and fetches the
     * specified feed as an InputStream.  The caller is responsible for calling
     * {@link InputStream#close()} on the returned {@link InputStream}.
     *
     * @param feedUrl The feed that should be fetched.
     * @param authToken The authentication token that should be used when
     * fetching the feed.
     * @param eTag The eTag associated with this request, this will 
     *             cause the GET to return a 304 if the content was
     *             not modified. The parameter can be null
     * @return An InputStream for the feed.
     * @throws IOException Thrown if an io error occurs while communicating with
     * the service.
     * @throws HttpException if the service returns an error response.
     */
    InputStream getFeedAsStream(String feedUrl,
                                String authToken,
                                String eTag)
        throws HttpException, IOException;

    /**
     * Connects to a GData server (specified by the mediaEntryUrl) and fetches the
     * specified media entry as an InputStream.  The caller is responsible for calling
     * {@link InputStream#close()} on the returned {@link InputStream}.
     *
     * @param mediaEntryUrl The media entry that should be fetched.
     * @param authToken The authentication token that should be used when
     * fetching the media entry.
     * @param eTag The eTag associated with this request, this will 
     *             cause the GET to return a 304 if the content was
     *             not modified.
     * @return An InputStream for the media entry.
     * @throws IOException Thrown if an io error occurs while communicating with
     * the service.
     * @throws HttpException if the service returns an error response.
     */
    InputStream getMediaEntryAsStream(String mediaEntryUrl, String authToken, String eTag)
        throws HttpException, IOException;

    // TODO: support batch update

    /**
     * Connects to a GData server (specified by the feedUrl) and creates a new
     * entry.  The response from the server is returned as an 
     * {@link InputStream}.  The caller is responsible for calling
     * {@link InputStream#close()} on the returned {@link InputStream}.
     * 
     * @param feedUrl The feed url where the entry should be created.
     * @param authToken The authentication token that should be used when 
     * creating the entry.
     * @param entry The entry that should be created.
     * @throws IOException Thrown if an io error occurs while communicating with
     * the service.
     * @throws HttpException if the service returns an error response.
     */
    InputStream createEntry(String feedUrl,
                            String authToken,
                            GDataSerializer entry)
        throws HttpException, IOException;

    /**
     * Connects to a GData server (specified by the editUri) and updates an
     * existing entry.  The response from the server is returned as an 
     * {@link InputStream}.  The caller is responsible for calling
     * {@link InputStream#close()} on the returned {@link InputStream}.
     * 
     * @param editUri The edit uri that should be used for updating the entry.
     * @param authToken The authentication token that should be used when 
     * updating the entry.
     * @param eTag The eTag associated with this request, this will 
     *             cause the PUT to return a conflict if the
     *             resource was already modified
     * @param entry The entry that should be updated.
     * @throws IOException Thrown if an io error occurs while communicating with
     * the service.
     * @throws HttpException if the service returns an error response.
     */
    InputStream updateEntry(String editUri,
                            String authToken,
                            String eTag,
                            GDataSerializer entry)
        throws HttpException, IOException;

    /**
     * Connects to a GData server (specified by the editUri) and deletes an
     * existing entry.
     * 
     * @param editUri The edit uri that should be used for deleting the entry.
     * @param authToken The authentication token that should be used when
     * deleting the entry.
     * @param eTag The eTag associated with this request, this will 
     *             cause a failure if the resource was modified
     *             since retrieval.
     * @throws IOException Thrown if an io error occurs while communicating with
     * the service.
     * @throws HttpException if the service returns an error response.
     */
    void deleteEntry(String editUri,
                     String authToken,
                     String eTag)
        throws HttpException, IOException;

    /**
     * Connects to a GData server (specified by the editUri) and updates an
     * existing media entry.  The response from the server is returned as an
     * {@link InputStream}.  The caller is responsible for calling
     * {@link InputStream#close()} on the returned {@link InputStream}.
     *
     * @param editUri The edit uri that should be used for updating the entry.
     * @param authToken The authentication token that should be used when
     * updating the entry 
     * @param eTag The eTag associated with this request, this will 
     *             cause the PUT to return a conflict if the
     *             resource was already modified
     * @param mediaEntryInputStream The {@link InputStream} that contains the new
     *   value of the resource
     * @param contentType The contentType of the new media entry
     * @throws IOException Thrown if an io error occurs while communicating with
     * the service.
     * @throws HttpException if the service returns an error response.
     * @return The {@link InputStream} that contains the metadata associated with the
     *   new version of the media entry.
     */
    public InputStream updateMediaEntry(String editUri, String authToken, String eTag,
            InputStream mediaEntryInputStream, String contentType)
        throws HttpException, IOException;

  /**
   * Connects to a GData server (specified by the batchUrl) and submits a
   * batch for processing.  The response from the server is returned as an
   * {@link InputStream}.  The caller is responsible for calling
   * {@link InputStream#close()} on the returned {@link InputStream}.
   *
   * @param batchUrl The batch url to which the batch is submitted.
   * @param authToken the authentication token that should be used when
   * submitting the batch.
   * @param batch The batch of entries to submit.
   * @throws IOException Thrown if an io error occurs while communicating with
   * the service.
   * @throws HttpException if the service returns an error response.
   */
  InputStream submitBatch(String batchUrl,
       String authToken,
       GDataSerializer batch)
       throws HttpException, IOException;
}
