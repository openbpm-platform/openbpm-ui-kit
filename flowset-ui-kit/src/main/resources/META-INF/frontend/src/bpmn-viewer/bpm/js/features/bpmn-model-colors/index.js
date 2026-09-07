/*
 * Copyright (c) Haulmont 2026. All Rights Reserved.
 * Use is subject to license terms.
 */

import BpmnModelColors from './BpmnModelColors';
import BpmnModelColorsRenderer from './BpmnModelColorsRenderer';

export default {
  __init__: ['bpmnModelColors', 'bpmnModelColorsRenderer'],
  bpmnModelColors: [ 'type', BpmnModelColors ],
  bpmnModelColorsRenderer: [ 'type', BpmnModelColorsRenderer ]
};
