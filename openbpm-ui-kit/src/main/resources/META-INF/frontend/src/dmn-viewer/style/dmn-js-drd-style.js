import {css} from 'lit';

export const dmnJsDrdStyles = css`

.dmn-drd-container {
  --dmn-definitions-background-color: var(--color-grey-225-10-97);
  --dmn-definitions-border-color: var(--color-grey-225-10-75);
  --dmn-definitions-focus-border-color: var(--color-grey-225-10-75);
  --dmn-definitions-error-color: var(--color-red-360-100-45);
  --dmn-definitions-error-border-color: var(--color-red-360-100-45);
  --dmn-definitions-error-background-color: var(--color-red-360-100-97);
  --drill-down-overlay-background-color: var(--color-blue-205-100-50);
  --drill-down-overlay-color: var(--color-white);
  --drd-font-family-monospace: monospace;

  width: 100%;
  height: 100%;
  position: relative;
}

.dmn-drd-container * {
  box-sizing: border-box;
}

.djs-overlay .drill-down-overlay {
  font-size: 16px;
  text-align: left;
  padding: 1px;
}

.djs-overlay .drill-down-overlay > button,
.djs-overlay .drill-down-overlay > span {
  display: block;
  padding: 2px 4px;
  margin: 0;
  border: none;
  border-radius: 1px;
  background: var(--drill-down-overlay-background-color);
  color: var(--drill-down-overlay-color);
  font-size: inherit;
  outline-offset: 2px;
}

.dmn-definitions {
  position: absolute;
  top: 20px;
  left: 20px;
  background-color: var(--dmn-definitions-background-color);
  border: solid 1px var(--dmn-definitions-border-color);
  border-radius: 2px;
  padding: 4px;
}

.dmn-definitions .dmn-definitions-name {
  font-size: 24px;
  padding: 3px;
}

.dmn-definitions .dmn-definitions-name:focus {
  outline: none;
}

.dmn-definitions .dmn-definitions-id {
  font-family: var(--drd-font-family-monospace);
  margin-top: 2px;
  padding: 3px;
}

.dmn-definitions .dmn-definitions-id:focus {
  outline: none;
}

.dmn-definitions > [contenteditable]:hover,
.dmn-definitions > [contenteditable]:focus {
  padding: 2px;
  background-color: var(--color-white);
  border-radius: 2px;
  border: 1px solid var(--dmn-definitions-focus-border-color);
}

.dmn-definitions .dmn-definitions-error-message {
  color: var(--dmn-definitions-error-color);
  display: block;
  font-size: 12px;
  margin-top: 6px;
}

.dmn-definitions .dmn-definitions-id.dmn-definitions-error {
  border-color: var(--dmn-definitions-error-border-color);
  background-color: var(--dmn-definitions-error-background-color);
}

.djs-container.with-palette .dmn-definitions {
  left: 80px;
}

.djs-container.with-palette-two-column .dmn-definitions {
  left: 130px;
}

`;