package com.example.spacecatsmarket.api.error;

import com.example.spacecatsmarket.application.exception.ProductNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import java.net.URI;
import java.util.stream.Collectors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	private static final URI VALIDATION_ERROR_TYPE = URI.create("https://cosmo-cats.market/errors/validation");
	private static final URI NOT_FOUND_ERROR_TYPE = URI.create("https://cosmo-cats.market/errors/not-found");

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex,
			HttpHeaders headers,
			HttpStatusCode status,
			WebRequest request
	) {
		String detail = ex.getBindingResult().getFieldErrors().stream()
				.map(error -> "Field '%s' %s.".formatted(error.getField(), error.getDefaultMessage()))
				.collect(Collectors.joining(" "));

		ProblemDetail problemDetail = buildProblemDetail(
				HttpStatus.BAD_REQUEST,
				VALIDATION_ERROR_TYPE,
				"Validation failed",
				detail,
				getRequestPath(request)
		);

		return ResponseEntity.badRequest().body(problemDetail);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ProblemDetail> handleConstraintViolation(
			ConstraintViolationException ex,
			HttpServletRequest request
	) {
		String detail = ex.getConstraintViolations().stream()
				.map(violation -> "Field '%s' %s.".formatted(
						violation.getPropertyPath(),
						violation.getMessage()
				))
				.collect(Collectors.joining(" "));

		ProblemDetail problemDetail = buildProblemDetail(
				HttpStatus.BAD_REQUEST,
				VALIDATION_ERROR_TYPE,
				"Validation failed",
				detail,
				request.getRequestURI()
		);

		return ResponseEntity.badRequest().body(problemDetail);
	}

	@ExceptionHandler(ProductNotFoundException.class)
	public ResponseEntity<ProblemDetail> handleProductNotFound(
			ProductNotFoundException ex,
			HttpServletRequest request
	) {
		ProblemDetail problemDetail = buildProblemDetail(
				HttpStatus.NOT_FOUND,
				NOT_FOUND_ERROR_TYPE,
				"Product not found",
				ex.getMessage(),
				request.getRequestURI()
		);

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problemDetail);
	}

	private ProblemDetail buildProblemDetail(
			HttpStatus status,
			URI type,
			String title,
			String detail,
			String instance
	) {
		ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, detail);
		problemDetail.setType(type);
		problemDetail.setTitle(title);
		problemDetail.setInstance(URI.create(instance));
		return problemDetail;
	}

	private String getRequestPath(WebRequest request) {
		if (request instanceof ServletWebRequest servletWebRequest) {
			return servletWebRequest.getRequest().getRequestURI();
		}
		return "/";
	}
}
