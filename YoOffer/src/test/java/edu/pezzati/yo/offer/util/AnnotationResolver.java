package edu.pezzati.yo.offer.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.NoSuchElementException;

public class AnnotationResolver {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static boolean givenTypeIsAnnotated(Class type, Class... annotations) {
		for (Class annotation : annotations) {
			if (!type.isAnnotationPresent(annotation)) {
				return false;
			}
		}
		return true;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static boolean givenFieldIsAnnotated(Field field, Class... annotations) {
		for (Class annotation : annotations) {
			if (!field.isAnnotationPresent(annotation))
				return false;
		}
		return true;
	}

	@SuppressWarnings("rawtypes")
	public static Annotation getTypeAnnotationByAnnotationType(Class type, Class annotationType) {
		for (Annotation annotation : type.getAnnotations()) {
			if (annotation.annotationType().equals(annotationType)) {
				return annotation;
			}
		}
		throw new NoSuchElementException();
	}

	@SuppressWarnings("rawtypes")
	public static Annotation getMethodAnnotationByAnnotationType(Method method, Class annotationType) {
		for (Annotation annotation : method.getAnnotations()) {
			if (annotation.annotationType().equals(annotationType)) {
				return annotation;
			}
		}
		throw new NoSuchElementException();
	}

	@SuppressWarnings("rawtypes")
	public static Annotation getFieldAnnotationByType(Field field, Class annotationType) {
		for (Annotation typeAnnotation : field.getAnnotations()) {
			if (typeAnnotation.annotationType().equals(annotationType))
				return typeAnnotation;
		}
		throw new NoSuchElementException();
	}
}
