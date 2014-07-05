package lichen.creeper.core.internal.override;

import org.apache.tapestry5.BaseValidationDecorator;
import org.apache.tapestry5.CSSClassConstants;
import org.apache.tapestry5.Field;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.ValidationDecorator;
import org.apache.tapestry5.ValidationTracker;
import org.apache.tapestry5.dom.Element;
import org.apache.tapestry5.services.Environment;
import org.apache.tapestry5.services.ValidationDecoratorFactory;

/**
 * 复写tapestry内置的错误修饰类，主要目的是去掉错误后面的红色标记
 * @author jcai
 */
public class CreeperValidationDecoratorFactory implements ValidationDecoratorFactory{
    private final Environment environment;

    public CreeperValidationDecoratorFactory(Environment environment)
    {
        this.environment = environment;
    }
    @Override
    public ValidationDecorator newInstance(MarkupWriter writer) {
        return new CreeperValidationDecorator(writer);
    }
    class CreeperValidationDecorator extends BaseValidationDecorator{

        private final MarkupWriter markupWriter;

        /**
         */
        public CreeperValidationDecorator(MarkupWriter markupWriter)
        {
            this.markupWriter = markupWriter;
        }

        @Override
        public void insideField(Field field)
        {
            if (inError(field))
                addErrorClassToCurrentElement();
        }

        @Override
        public void insideLabel(Field field, Element element)
        {
            if (field == null)
                return;

            if (inError(field))
                element.addClassName(CSSClassConstants.ERROR);
        }

        /**
         * Writes an icon for field after the field. The icon has the same id as the field, with ":icon" appended. This is
         * expected by the default client-side JavaScript. The icon's src is a blank spacer image (this is to allow the
         * image displayed to be overridden via CSS). The icon's CSS class is "t-error-icon", with "t-invisible" added
         * if the field is not in error when rendered. If client validation is not enabled for the form containing the
         * field and the field is not in error, then the error icon itself is not rendered.
         *
         * @param field
         *            which just completed rendering itself
         */
        @Override
        public void afterField(Field field)
        {
        }

        private boolean inError(Field field)
        {
            ValidationTracker tracker = environment.peekRequired(ValidationTracker.class);

            return tracker.inError(field);
        }

        private void addErrorClassToCurrentElement()
        {
            markupWriter.getElement().addClassName(CSSClassConstants.ERROR);
        }
    }
}
