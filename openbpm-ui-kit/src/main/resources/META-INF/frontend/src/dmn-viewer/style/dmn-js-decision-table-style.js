import {css} from 'lit';

export const dmnJsDecisionTableStyles = css`

.dmn-decision-table-container {
  --action-icon-color: var(--color-blue-205-100-50);
  --action-icon-background-color: transparent;
  --action-icon-hover-background-color: var(--color-blue-205-100-45);
  --action-icon-hover-color: var(--color-white);
  --add-column-icon-color: var(--color-white);
  --add-column-icon-background-color: var(--color-grey-225-10-35);
  --decision-table-color: var(--color-grey-225-10-15);
  --decision-table-background-color: var(--color-white);
  --decision-table-properties-color: var(--color-grey-225-10-15);
  --dmn-expression-language-background-color: var(--color-blue-205-100-45-opacity-30);
  --dmn-expression-language-color: var(--color-white);
  --dmn-expression-language-hover-background-color: var(--color-blue-205-100-50);
  --drag-and-drop-drag-color: var(--color-grey-225-10-55);
  --drag-and-drop-drop-marker-color: var(--color-grey-225-10-35);
  --hit-policy-explanation-color: var(--color-grey-225-10-35);
  --powered-by-overlay-background-color: var(--color-black-opacity-20);
  --powered-by-overlay-content-background-color: var(--color-white);
  --powered-by-overlay-content-color: var(--color-grey-225-10-15);
  --powered-by-overlay-content-border-color: var(--color-grey-225-10-75);
  --simple-mode-button-color: var(--color-grey-225-10-15);
  --simple-mode-button-background-color: var(--color-grey-225-10-97);
  --simple-mode-button-border-color: var(--color-grey-225-10-75);
  --simple-mode-button-box-shadow-color: var(--color-black-opacity-10);
  --simple-mode-button-disabled-color: var(--color-grey-225-10-75);
  --table-cell-color: var(--color-grey-225-10-15);
  --table-cell-border-color: var(--color-grey-225-10-15);
  --table-cell-selected-outline-color: var(--color-blue-205-100-45);
  --table-foot-add-rule-border-color: var(--color-grey-225-10-75);
  --table-foot-add-rule-color: var(--color-grey-225-10-75);
  --table-foot-add-rule-hover-border-color: var(--color-grey-225-10-15);
  --table-foot-add-rule-hover-color: var(--color-grey-225-10-15);
  --table-head-border-color: var(--color-grey-225-10-15);
  --table-head-clause-color: var(--color-grey-225-10-35);
  --table-head-separator-color: var(--color-grey-225-10-75);
  --table-head-variable-color: var(--color-grey-225-10-35);
  --table-row-alternative-background-color: var(--color-grey-225-10-97);
  --view-drd-button-background-color: var(--color-white);
  --view-drd-button-border-color: var(--color-grey-225-10-75);
  --view-drd-button-color: var(--color-grey-225-10-15);
  --view-drd-button-hover-background-color: var(--color-grey-225-10-97);
  --decision-table-font-family: 'Arial', sans-serif;

  width: 100%;
  height: 100%;
  overflow: hidden;
  position: relative;
  font-family: var(--decision-table-font-family);
  font-size: 14px;
  color: var(--table-color);
  max-height: 100%;
}

/* basic styles */

.dmn-decision-table-container * {
  box-sizing: border-box;
}

.dmn-decision-table-container .tjs-container {
  display: flex;
  flex-direction: column;
  width: min-content;
  max-width: 100%;
  height: 100%;
}

/* end basic styles */

/* basic table styles */

.dmn-decision-table-container .tjs-table-container {
  overflow: auto;
  border: solid 1px var(--table-cell-border-color);
  border-top: none;
}

.dmn-decision-table-container .tjs-table {
  max-width: 100%;
  max-height: 100%;
  position: relative;
  width: 100%; /* Fallback for legacy Edge */
  width: min-content;
  table-layout: fixed;
  border-collapse: separate;
  border-spacing: 0;
}

.dmn-decision-table-container th,
.dmn-decision-table-container td {
  position: relative;
  white-space: pre-wrap;
  overflow-wrap: break-word;
  background-clip: padding-box;
}

.dmn-decision-table-container thead th {
  text-align: center;
  width: 192px;
  font-weight: normal;
  position: sticky;
  top: 0;
  z-index: 1;
  background-color: var(--decision-table-background-color);
  border-bottom: 3px double var(--table-head-border-color);
}

.dmn-decision-table-container thead th.output-editor {
  z-index: 2;
}

.dmn-decision-table-container thead th.create-inputs,
.dmn-decision-table-container thead th.input-editor {
  z-index: 3;
}

.dmn-decision-table-container tbody td {
  border-top: solid 1px var(--table-cell-border-color);
}

.dmn-decision-table-container th.input-cell,
.dmn-decision-table-container td.input-cell,
.dmn-decision-table-container th.output-cell,
.dmn-decision-table-container td.output-cell,
.dmn-decision-table-container th.annotation,
.dmn-decision-table-container td.annotation {
  color: var(--table-cell-color);
}

.dmn-decision-table-container th.input-cell + th.output-cell,
.dmn-decision-table-container td.input-cell + td.output-cell {
  border-left: 3px double var(--table-cell-border-color);
}

.dmn-decision-table-container th:not(:first-child),
.dmn-decision-table-container td:not(:first-child) {
  padding: 4px;
  border-left: solid 1px var(--table-cell-border-color);
}

.dmn-decision-table-container thead {
  background-color: var(--decision-table-background-color);
}

.dmn-decision-table-container thead th:first-child {
  left: 0;
  z-index: 4;
}

.dmn-decision-table-container tfoot td:first-child {
  border-right: solid 1px var(--table-foot-add-rule-border-color);
}

.dmn-decision-table-container tbody td:first-child,
.dmn-decision-table-container tfoot td:first-child {
  position: sticky;
  left: 0;
  background-color: var(--decision-table-background-color);
  z-index: 1;
}

.dmn-decision-table-container tbody:empty {
  display: none;
}

.dmn-decision-table-container thead th:first-child,
.dmn-decision-table-container tbody td:first-child {
  border-right: solid 1px var(--table-cell-border-color);
}

.dmn-decision-table-container thead th:nth-child(2),
.dmn-decision-table-container tbody td:nth-child(2),
.dmn-decision-table-container tfoot td:nth-child(2) {
  border-left: none;
}

.dmn-decision-table-container tbody tr:last-child td {
  border-bottom: solid 1px var(--table-cell-border-color);
}

.dmn-decision-table-container tbody tr:first-child td {
  border-top: none;
}

.dmn-decision-table-container th.index-column {
  width: 56px;
}

.dmn-decision-table-container td.rule-index {
  text-align: right;
  padding-right: 8px;
}

.dmn-decision-table-container tbody tr:nth-child(2n) {
  background-color: var(--table-row-alternative-background-color);
}

.dmn-decision-table-container td.input-cell.add-rule,
.dmn-decision-table-container td.input-cell:not(.focussed).empty {
  text-align: center;
}

/* end basic table styles */

/* selection styles */

.dmn-decision-table-container h3,
.dmn-decision-table-container h5 {
  position: relative;
}

.dmn-decision-table-container .selected {
  outline: var(--table-cell-selected-outline-color);
  outline-offset: -1px;
}

.dmn-decision-table-container th.selected:not(.focussed) {
  background: var(--selected-not-focused-border-color);
}

/* end selection styles */

/* footer styles */

.dmn-decision-table-container tfoot td.input-cell + td.output-cell {
  border-left: 3px double var(--table-foot-add-rule-border-color);
}

.dmn-decision-table-container tfoot.add-rule td.add-rule-add {
  text-align: right;
  padding-right: 4px;
}

.dmn-decision-table-container tfoot.add-rule tr td {
  border-color: var(--table-foot-add-rule-border-color);
  color: var(--table-foot-add-rule-color);
}

.dmn-decision-table-container tfoot.add-rule:hover td.input-cell + td.output-cell {
  border-left-color: var(--table-cell-border-color);
}

.dmn-decision-table-container tfoot.add-rule:hover td {
  border-color: var(--table-foot-add-rule-hover-border-color);
  color: var(--table-foot-add-rule-hover-color);
}

/* end footer styles */

/* content editable styles */

.dmn-decision-table-container [contenteditable],
.dmn-decision-table-container [tabindex] {
  outline: none;
}

/* end content editable styles */

/* decision table header */
.dmn-decision-table-container .decision-table-header {
  text-align: left;
  padding: 10px;
}

.dmn-decision-table-container .decision-table-header * {
  vertical-align: middle;
}

/* decision table properties */
.dmn-decision-table-container .decision-table-properties {
  display: flex;
  color: var(--decision-table-properties-color);
  border: 1px solid var(--table-cell-border-color);
  background-color: var(--decision-table-background-color);
  padding: 10px;
  align-items: center;
}

.dmn-decision-table-container .decision-table-name {
  position: relative;
  display: block;
  flex-grow: 1;
  margin: 0;
  width: 0;
  min-width: 100px;
  max-width: min-content;
  font-size: 21px;
  font-weight: normal;
}

.dmn-decision-table-container .decision-table-name:not(.editable),
.dmn-decision-table-container .decision-table-name .content-editable {
  text-overflow: ellipsis;
  white-space: nowrap;
  overflow: hidden;
}

.dmn-decision-table-container .decision-table-name.focussed .content-editable {
  text-overflow: unset;
}

.dmn-decision-table-container .decision-table-header-separator {
  display: block;
  height: 32px;
  border-left: 2px solid var(--table-head-separator-color);
  margin: 0 10px;
}

.dmn-decision-table-container .hit-policy {
  display: block;
}

.dmn-decision-table-container .hit-policy-explanation {
  margin-left: 5px;
  color: var(--hit-policy-explanation-color);
}

/* end decision table properties */

.dmn-decision-table-container thead .input-label,
.dmn-decision-table-container thead .input-expression,
.dmn-decision-table-container thead .output-label,
.dmn-decision-table-container thead .output-name {
  display: -webkit-box;
  margin: 16px 6px;
  font-size: 16px;
  overflow: hidden;
  text-overflow: ellipsis;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
}

.dmn-decision-table-container thead .input-expression:empty::before {
  content: 'Input';
  opacity: 0.5;
}

.dmn-decision-table-container thead .output-name:empty::before {
  content: 'Output';
  opacity: 0.5;
}

.dmn-decision-table-container thead .input-variable,
.dmn-decision-table-container thead .output-variable {
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  text-align: right;
}

.dmn-decision-table-container thead .input-cell .clause,
.dmn-decision-table-container thead .output-cell .clause {
  font-size: 12px;
  text-align: left;
  color: var(--table-head-clause-color);
}

.dmn-decision-table-container thead .input-cell .input-variable,
.dmn-decision-table-container thead .output-cell .output-variable {
  font-size: 12px;
  color: var(--table-head-variable-color);
}

/* actionable icon */

.dmn-decision-table-container .action-icon {
  border: none;
  border-radius: 2px;
  box-shadow: 0 0 0 2px var(--decision-table-background-color);
  color: var(--action-icon-color);
  background-color: var(--action-icon-background-color);
  padding: 0;
}

.dmn-decision-table-container .create-inputs .action-icon,
.dmn-decision-table-container .add-input .action-icon,
.dmn-decision-table-container .add-output .action-icon {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 16px;
  height: 16px;
  margin: 0;
  border-radius: 50%;
  color: var(--add-column-icon-color);
  background-color: var(--add-column-icon-background-color);
  outline-offset: 4px;
}

.dmn-decision-table-container .actionable:hover .action-icon {
  background-color: var(--action-icon-hover-background-color);
  color: var(--action-icon-hover-color);
}

.dmn-decision-table-container .action-icon::before {
  margin-left: 0.1em;
  margin-right: 0.1em;
}

.dmn-decision-table-container .add-input,
.dmn-decision-table-container .add-output {
  position: absolute;
  top: 50%;
  right: -1px;
  transform: translate(50%, -50%);
  z-index: 2;
  border-radius: 100%;
  padding: 6px;
}

/* end actionable icon */

/* view drd */
.dmn-decision-table-container .view-drd {
  margin-bottom: 10px;
}

.dmn-decision-table-container .view-drd .view-drd-button {
  background: var(--view-drd-button-background-color);
  padding: 8px;
  border: solid 1px var(--view-drd-button-border-color);
  border-radius: 2px;
  font-size: 14px;
  color: var(--view-drd-button-color);
  font-weight: bold;
  cursor: pointer;
}

.dmn-decision-table-container .view-drd .view-drd-button:hover {
  background: var(--view-drd-button-hover-background-color);
}

/* end view drd */

/* simple mode */

.dmn-decision-table-container .simple-mode-button {
  color: var(--simple-mode-button-color);
  background-color: var(--simple-mode-button-background-color);
  position: absolute;
  padding: 4px;
  border-radius: 2px;
  border: solid 1px var(--simple-mode-button-border-color);
  cursor: pointer;
  font-size: 14px;
  z-index: 5;
  opacity: 0.8;
  box-shadow: 1px 1px 1px 1px var(--simple-mode-button-box-shadow-color);
}

.dmn-decision-table-container .simple-mode-button:hover {
  opacity: 1;
}

.dmn-decision-table-container .simple-mode-button.disabled,
.dmn-decision-table-container .simple-mode-button.disabled:hover {
  color: var(--simple-mode-button-disabled-olor);
}

.dmn-decision-table-container .dms-input-duration-edit-row {
  align-items: flex-start;
}

/* end simple mode  */

/* badges */

.dms-badge {
  border-radius: 2px;
  padding: 3px 6px;
  font-size: 0.8em;
  height: 19px;
}

.dms-badge-icon + .dms-badge-label {
  margin-left: 3px;
}

.dms-badge.dmn-expression-language {
  background: var(--dmn-expression-language-background-color);
  color: var(--dmn-expression-language-color);
}

.dms-badge + .dms-badge {
  margin-left: 3px;
}

.dmn-decision-table-container .dmn-expression-language .dms-badge-label {
  display: none;
}

.dmn-decision-table-container .input-cell:hover .dmn-expression-language .dms-badge-label,
.dmn-decision-table-container .output-cell:hover .dmn-expression-language .dms-badge-label {
  display: inline-block;
}

.dmn-decision-table-container .input-editor .dmn-expression-language {
  margin-top: 4px;
  display: inline-block;
}

.dmn-decision-table-container .cell .dms-badge.dmn-expression-language {
  z-index: -1;
}

.dmn-decision-table-container .cell:hover .dms-badge.dmn-expression-language {
  background: var(--dmn-expression-language-hover-background-color);
  z-index: 1;
}

/* cell expression language */

.dmn-decision-table-container .cell .dmn-expression-language {
  display: flex;
  align-items: center;
  position: absolute;
  top: 2px;
  right: 2px;
  pointer-events: none;
}

/* end cell expression language */

/* drag and drop */

.dmn-decision-table-container .dragover::before {
  content: '';
  display: block;
  position: absolute;
  background-color: var(--drag-and-drop-drop-marker-color);
  z-index: 2;
  pointer-events: none;
}

.dmn-decision-table-container .dragover.top::before {
  left: 0;
  right: 0;
  height: 5px;
  top: 0;
}

.dmn-decision-table-container .dragover.right::before {
  top: 0;
  bottom: 0;
  width: 5px;
  right: 0;
}

.dmn-decision-table-container .dragover.bottom::before {
  left: 0;
  right: 0;
  height: 5px;
  bottom: 0;
}

.dmn-decision-table-container .dragover.left::before {
  top: 0;
  bottom: 0;
  width: 5px;
  left: 0;
}

.dmn-decision-table-container .dragged {
  color: var(--drag-and-drop-drag-color);
}

/* cell description */

.dmn-decision-table-container .description-indicator {
  position: absolute;
  top: 0;
  right: -4px;
  border-left: 4px solid transparent;
  border-right: 4px solid transparent;
  border-bottom: 4px solid var(--color-black);
  transform: rotate(45deg);
  transform-origin: top;
}

/* end cell description */

`;