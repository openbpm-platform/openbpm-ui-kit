/*
 * Copyright (c) Haulmont 2026. All Rights Reserved.
 * Use is subject to license terms.
 */

import {ElementLike} from "diagram-js/lib/model/Types";

export default class BpmnModelColors {
    /**
     * Whether the colors of the diagram elements set in BPMN XML should be shown.
     */
    isVisible(): boolean;

    /**
     * Sets whether the colors of the diagram elements set in BPMN XML should be shown.
     * @param visible need to show colors set in BPMN XML ir not
     */
    setVisible(visible: boolean): void;

    /**
     * Whether the custom color is set in the BPMN XML for the specified diagram element.
     * @param element element from BPMN XML
     */
    isModelColored(element: ElementLike): boolean;

    /**
     * 
     * @param element
     */
    markProgrammaticallyColored(element: ElementLike): void;
}
