package ch.rhb.integra.common;

import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * An immutable data structure representing HTTP status response bodies. It
 * supports XML and JSON representation. A JSON representation of this class
 * would be something like the following:
 *
 * <pre>
 *     {
 *         "status_code": 200,
 *         "reason_phrase": "OK",
 *         "message" : "Successfully processed request."
 *     }
 * </pre>
 *
 * or
 *
 * <pre>
 *     {
 *         "status_code": 404,
 *         "reason_phrase": "Not Found",
 *         "message": "Something went wrong",
 *         "errors": [
 *             {"code": "res-15", "message": "some error message"},
 *             {"code": "res-16", "message": "yet another message"}
 *         ]
 *     }
 * </pre>
 *
 * @author esentri AG, <a href="mailto:markus.lohn@esentri.com">mlohn</a>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "statusCode", "reasonPhrase", "message", "id", "errors" })
@XmlRootElement(name = "DefaultResponse")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class DefaultResponse {

	/**
	 * The 4xx or 5xx status code for error cases, e.g. 404
	 */
	@XmlElement(name = "status_code", required = true)
	private int statusCode = HttpStatus.OK.value();

	/**
	 * The HTTP reason phrase corresponding the {@linkplain #statusCode}, e.g. Not
	 * Found
	 *
	 * @see <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec6.html"> Status
	 *      Code and Reason Phrase</a>
	 */
	@XmlElement(name = "reason_phrase", required = true)
	private String reasonPhrase = HttpStatus.OK.getReasonPhrase();

	/**
	 * A simple human readable message for the end user.
	 */
	private String message;

	/**
	 * List of application-level error code and message combinations. Using these
	 * errors we provide more information about the actual error
	 */
	@XmlElementWrapper(name = "errors")
	@XmlElement(name = "error")
	private List<ApiError> errors;

	/**
	 * Private constructor to enforce the usage of a factory function to create new
	 * objects of this class.
	 *
	 * @param statusCode
	 *            the status code as number
	 * @param reason
	 *            a simple message corresponding to the statusCode
	 * @param message
	 *            a human readable message
	 * @param errors
	 *            a list with detailed error descriptions
	 */
	private DefaultResponse(int statusCode, String reason, String message, List<ApiError> errors) {
		this.statusCode = statusCode;
		this.reasonPhrase = reason;
		this.message = message;
		this.errors = errors;
	}

	/**
	 * Static factory method to create a {@linkplain DefaultResponse} with a
	 * human readable message and a given HTTP status code.
	 *
	 * @param status
	 *            the HTTP status code, like 200 or 400
	 * @param message
	 *            a human readable message
	 */
	public static DefaultResponse createResponse(HttpStatus status, String message) {
		return new DefaultResponse(status.value(), status.getReasonPhrase(), message,
				Collections.emptyList());
	}

	/**
	 * Static factory method to create a {@linkplain DefaultResponse} of type
	 * success with a human readable message. It uses the HTTP status code 200 by
	 * default.
	 *
	 * @param message
	 *            a human readable message
	 */
	public static DefaultResponse createSuccessResponse(String message) {
		return createResponse(HttpStatus.OK, message);
	}


	/**
	 * Static factory method to create a {@linkplain DefaultResponse} of type
	 * error with a human readable message and a given HTTP status code.
	 *
	 * @param status
	 *            the HTTP status code, like 200 or 400
	 * @param message
	 *            a human readable message
	 * @param errors
	 *            a list with detailed error descriptions
	 */
	public static DefaultResponse createErrorResponse(HttpStatus status, String message, List<ApiError> errors) {
		return new DefaultResponse(status.value(), status.getReasonPhrase(), message, errors);
	}

	/**
	 * Static factory method to create a {@linkplain DefaultResponse} of type
	 * error with a human readable message and a given HTTP status code.
	 *
	 * @param status
	 *            the HTTP status code, like 200 or 400
	 * @param message
	 *            a human readable message
	 * @param error
	 *            a detailed error descriptions
	 */
	public static DefaultResponse createErrorResponse(HttpStatus status, String message, ApiError error) {
		return createErrorResponse(status, message, Collections.singletonList(error));
	}

	/**
	 * Static factory method to create a {@linkplain DefaultResponse} of type
	 * error with a human readable message. It uses the HTTP status code 500 by
	 * default.
	 *
	 * @param message
	 *            a human readable message
	 * @param errors
	 *            a list with detailed error descriptions
	 */
	public static DefaultResponse createGenericErrorResponse(String message, List<ApiError> errors) {
		return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, message, errors);
	}

	/**
	 * Static factory method to create a {@linkplain DefaultResponse} of type
	 * error with a human readable message. It uses the HTTP status code 500 by
	 * default.
	 *
	 * @param message
	 *            a human readable message
	 * @param error
	 *            a detailed error descriptions
	 */
	public static DefaultResponse createGenericErrorResponse(String message, ApiError error) {
		return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, message, error);
	}

	/**
	 * Indicates whether this Response object is an error or a simple status
	 * message.
	 *
	 * @return true if an error response otherwise false
	 */
	public boolean isError() {
		return (statusCode >= 400 && errors.size() > 0);
	}

	/**
	 * An immutable data structure representing each application-level error. JSON
	 * representation of this class would be something like the following:
	 *
	 * <pre>
	 *     {"code": "res-12", "message": "some error"}
	 * </pre>
	 */
	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = { "code", "message" })
	@Getter
	@Setter
	@ToString
	@AllArgsConstructor
	public static class ApiError {
		/**
		 * The error code
		 */
		@XmlElement(required = true)
		private String code = null;

		/**
		 * Possibly localized error message
		 */
		@XmlElement(required = true)
		private String message = null;

	}

}
