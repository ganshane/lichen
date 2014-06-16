package creeper.core.internal.override;

import org.apache.tapestry5.*;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.dom.Element;
import org.apache.tapestry5.services.Environment;
import org.apache.tapestry5.services.FormSupport;
import org.apache.tapestry5.services.ValidationDecoratorFactory;

/**
 * @author jcai
 */
public class CreeperValidationDecoratorFactory implements ValidationDecoratorFactory{
    private final Environment environment;

    private final Asset spacerImage;

    public CreeperValidationDecoratorFactory(Environment environment, @Path("${tapestry.spacer-image}")
    Asset spacerImage)
    {
        this.environment = environment;
        this.spacerImage = spacerImage;
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

        private FormSupport getFormSupport()
        {
            return environment.peekRequired(FormSupport.class);
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
