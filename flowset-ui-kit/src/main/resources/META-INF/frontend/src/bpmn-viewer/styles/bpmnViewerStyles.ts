/*
 * Copyright (c) Haulmont 2025. All Rights Reserved.
 * Use is subject to license terms.
 */

import {css} from 'lit';

export const bpmnViewerStyles = css`
    .djs-container > svg:focus {
        outline: none;
    }

    .finished-activity:not(.djs-connection) .djs-visual {
        filter: var(--bpmn-finished-activity-filter);
    }

    .finished-activity:not(.djs-connection) .djs-visual > :nth-child(1) {
        stroke: var(--bpmn-finished-activity-stroke-color) !important;
        stroke-width: var(--bpmn-finished-activity-stroke-width) !important;
        filter: drop-shadow(0 0 var(--bpmn-finished-activity-glow-size) var(--bpmn-finished-activity-glow-color));
    }
    
    .passed-flow.djs-connection .djs-visual > path {
        stroke: var(--bpmn-passed-flow-stroke-color) !important;
        stroke-width: var(--bpmn-finished-activity-stroke-width) !important;
    }

    .passed-flow.djs-connection .djs-visual > defs > marker > path {
        stroke: var(--bpmn-passed-flow-stroke-color) !important;
        fill: var(--bpmn-passed-flow-stroke-color) !important;
    }

    .djs-element > .djs-visual {
        transition: filter 0.25s;
    }

    /* When the canvas is in the "not passed as disabled" state, every element without
       a token-history marker (and every label of such an element) is rendered the way
       disabled UI elements are */
    .bpmn-not-passed-as-disabled .djs-element:not(.running-activity):not(.finished-activity):not(.passed-flow):not(.marked-element-label) > .djs-visual {
        filter: var(--bpmn-disabled-element-filter);
    }


    .running-activity:not(.djs-connection) .djs-visual > :nth-child(1) {
        stroke: var(--bpmn-running-activity-stroke-color) !important;
        stroke-width: var(--bpmn-finished-activity-stroke-width) !important;
        filter: drop-shadow(0 0 var(--bpmn-running-activity-glow-size) var(--bpmn-running-activity-glow-color));
    }

    .modification-source-activity:not(.djs-connection) .djs-visual {
        outline: var(--bpmn-select-overlay-width) solid var(--bpmn-modification-source-activity-stroke-color) !important;
        border-radius: var(--bpmn-select-overlay-border-radius);
        outline-offset: var(--bpmn-select-overlay-offset);
    }

    .modification-target-activity:not(.djs-connection) .djs-visual {
        outline: var(--bpmn-select-overlay-width) solid var(--bpmn-modification-target-activity-stroke-color) !important;
        border-radius: var(--bpmn-select-overlay-border-radius);
        outline-offset: var(--bpmn-select-overlay-offset);
    }

    .activity-hover:not(.djs-connection) .djs-visual {
        cursor: pointer;
        outline: var(--bpmn-select-overlay-width) solid var(--bpmn-activity-hover-stroke-color) !important;
        border-radius: var(--bpmn-select-overlay-border-radius);
        outline-offset: var(--bpmn-select-overlay-offset);
    }

    .modification-source-activity-hover.activity-hover:not(.djs-connection) .djs-visual {
        outline-color: var(--bpmn-modification-source-hover-stroke-color) !important;
    }

    .modification-target-activity-hover.activity-hover:not(.djs-connection) .djs-visual {
        outline-color: var(--bpmn-modification-target-hover-stroke-color) !important;
    }

    .primary-color-activity:not(.djs-connection) .djs-visual {
        outline: var(--bpmn-select-overlay-width) solid var(--bpmn-activity-primary-stroke-color) !important;
        border-radius: var(--bpmn-select-overlay-border-radius);
        outline-offset: var(--bpmn-select-overlay-offset);
    }

    .activity-statistics-overlay {
        display: flex;
        flex-direction: row;
        gap: var(--lumo-space-xs);
    }

    .running-instances-overlay {
        background-color: var(--bpmn-running-instances-overlay-bg-color);
        color: var(--bpmn-running-instances-overlay-text-color);
        border-radius: var(--lumo-border-radius-m);
        line-height: var(--default-bpmn-element-overlay-size);
        padding: 0.1em;
        text-align: center;
        vertical-align: middle;
        font-size: var(--default-bpmn-element-overlay-font-size);
        font-weight: bold;
        border: var(--bpmn-running-instances-overlay-border);
        min-width: var(--default-bpmn-element-overlay-size);
        height: var(--default-bpmn-element-overlay-size);
        display: flex;
        justify-content: center;
    }

    .overlay-hidden {
        display: none;
    }

    .variable-change-overlay {
        min-width: 1.5em;
        height: 1.5em;
        border-radius: 1em;
        font-family: var(--lumo-font-family);
        padding: var(--lumo-space-xs);
        box-sizing: border-box;
        display: inline-flex;
        align-items: center;
        justify-content: center;
        line-height: 1.5em;
        font-weight: 600;
        font-size: var(--lumo-font-size-s);
        border: 1px solid var(--lumo-contrast-20pct);
    }

    .variable-change-overlay-root {
        display: flex;
        justify-content: center;
    }

    .variable-change-create {
        color: var(--lumo-success-text-color);
        background-color: var(--lumo-success-color-10pct);
        border-color: var(--lumo-success-color-50pct);
    }

    .variable-change-update {
        color: var(--lumo-primary-text-color);
        background-color: var(--lumo-primary-color-10pct);
        border-color: var(--lumo-primary-color-50pct);
    }

    .variable-change-delete {
        color: var(--lumo-error-text-color);
        background-color: var(--lumo-error-color-10pct);
        border-color: var(--lumo-error-color-50pct);
    }

    .variable-multiple-changes-overlay {
        font-family: var(--lumo-font-family);
        box-sizing: border-box;
        width: auto;
        min-width: 1.5em;
        align-items: center;
        padding: var(--lumo-space-xs);
        padding-right: var(--lumo-space-s);
    }

    .variable-change-changes-icon {
        width: 1.25em;
        height: 1.25em;
        flex-shrink: 0;
        transform: scaleX(-1);
    }

    .incident-overlay {
        background-color: var(--bpmn-incident-overlay-bg-color);
        color: var(--bpmn-incident-overlay-text-color);
        border-radius: var(--lumo-border-radius-m);
        line-height: var(--default-bpmn-element-overlay-size);
        padding: 0.1em;
        text-align: center;
        vertical-align: middle;
        font-size: var(--default-bpmn-element-overlay-font-size);
        font-weight: bold;
        border: var(--bpmn-incident-overlay-border);
        min-width: var(--default-bpmn-element-overlay-size);
        height: var(--default-bpmn-element-overlay-size);
        display: flex;
        justify-content: center;
    }

    .activity-instance-statistics-overlay {
        display: flex;
        flex-direction: column;
        align-items: flex-start;
        gap: 1px;
    }

    .activity-instance-statistics-overlay .statistics-line {
        display: flex;
        flex-direction: row;
        gap: 2px;
    }

    .bpmn-hide-completed-count .statistics-line-completed {
        display: none;
    }

    .active-tokens-overlay,
    .completed-activity-overlay {
        background-color: var(--bpmn-active-tokens-overlay-bg-color);
        color: var(--bpmn-active-tokens-overlay-text-color);
        border: var(--bpmn-active-tokens-overlay-border);
        border-radius: var(--lumo-border-radius-m);
        line-height: var(--default-bpmn-element-overlay-size);
        padding: 0 0.15em;
        text-align: center;
        font-size: var(--bpmn-activity-instance-statistics-overlay-font-size);
        font-weight: bold;
        min-width: var(--default-bpmn-element-overlay-size);
        height: var(--default-bpmn-element-overlay-size);
        display: flex;
        align-items: center;
        justify-content: center;
        gap: 0.15em;
    }

    .completed-activity-overlay {
        background-color: var(--bpmn-completed-activity-overlay-bg-color);
        color: var(--bpmn-completed-activity-overlay-text-color);
        border: var(--bpmn-completed-activity-overlay-border);
    }
    
    .activity-instance-statistics-overlay .incident-overlay {
        padding: 0 0.15em;
        align-items: center;
        gap: 0.15em;
        font-size: var(--bpmn-activity-instance-statistics-overlay-font-size);
        border-width: 1.5px;
    }

    .badge-icon {
        display: flex;
    }

    .badge-icon svg {
        width: 0.8em;
        height: 0.8em;
        display: block;
    }

    .navigation-overlay {
        background-color: var(--bpmn-navigation-overlay-background);
        cursor: pointer;
        display: flex;
        border-radius: var(--lumo-border-radius-m);
        justify-content: center;
        align-items: center;
        width: calc(var(--default-bpmn-element-overlay-size) + 2px);
        height: calc(var(--default-bpmn-element-overlay-size) + 2px);
    }

    .documentation-overlay {
        background-color: var(--bpmn-navigation-overlay-background);
        cursor: pointer;
        display: flex;
        border-radius: var(--lumo-border-radius-m);
        justify-content: center;
        align-items: center;
        width: calc(var(--default-bpmn-element-overlay-size) + 2px);
        height: calc(var(--default-bpmn-element-overlay-size) + 2px);
    }

    .send-message-overlay {
        background-color: var(--bpmn-send-message-overlay-background);
        fill: var(--bpmn-send-message-overlay-fill);
        cursor: pointer;
        display: flex;
        border-radius: var(--lumo-border-radius-m);
        justify-content: center;
        align-items: center;
        width: calc(var(--default-bpmn-element-overlay-size) + 2px);
        height: calc(var(--default-bpmn-element-overlay-size) + 2px);
    }

    .overlay-group-item-root {
        background-color: var(--bpmn-group-overlay-background-color);
        padding: 2px;
        border-radius: inherit;
    }

    .djs-overlays > .djs-overlay-send-message:first-child:not(:only-child) {
        border-radius: 0 0 var(--lumo-border-radius-m) var(--lumo-border-radius-m);
        border-bottom: var(--bpmn-group-overlay-border);
        border-left: var(--bpmn-group-overlay-border);
        border-right: var(--bpmn-group-overlay-border);
    }

    .djs-overlays > .djs-overlay-documentation:last-child:not(:only-child) {
        border-radius: var(--lumo-border-radius-m) var(--lumo-border-radius-m) 0 0;
        border-top: var(--bpmn-group-overlay-border);
        border-left: var(--bpmn-group-overlay-border);
        border-right: var(--bpmn-group-overlay-border);
    }

    .djs-overlays > .djs-overlay-send-message:only-child,
    .djs-overlays > .djs-overlay-documentation:only-child {
        border-radius: var(--lumo-border-radius-m);
        border: var(--bpmn-group-overlay-border);
    }

    .transaction-boundary-vertical-overlay {
        background: var(--bpmn-async-transaction-overlay-vertical-background);
    }

    .transaction-boundary-horizontal-overlay {
        background: var(--bpmn-async-transaction-boundary-overlay-horizontal-background);
    }

    .transaction-boundary-vertical-overlay.engine-wait-state {
        background: var(--bpmn-engine-transaction-overlay-vertical-background);
    }

    .transaction-boundary-horizontal-overlay.engine-wait-state {
        background: var(--bpmn-engine-transaction-boundary-overlay-horizontal-background);
    }

    .bpmn-animation-overlay-container {
        position: absolute;
        width: 100%;
        height: 100%;
        top: 0;
        left: 0;
        pointer-events: none;
        overflow: visible;
    }

    @keyframes pulse-glow {
        0% {
            box-shadow: 0 0 0 0 var(--bpmn-animation-overlay-pulse-grow-color-1);
            border-width: 3px;
            opacity: 0.7;
        }
        70% {
            box-shadow: 0 0 0 10px var(--bpmn-animation-overlay-pulse-grow-color-2);
            border-width: 5px;
            opacity: 0.9;
        }
        100% {
            box-shadow: 0 0 0 0 var(--bpmn-animation-overlay-pulse-grow-color-3);
            border-width: 3px;
            opacity: 0.7;
        }
    }

    @keyframes fadeout {
        from {
            opacity: 1;
            transform: scale(1);
        }
        to {
            opacity: 0;
            transform: scale(0.95);
        }
    }


    .bpmn-animation-overlay-fadeout {
        animation: fadeout 0.5s ease forwards !important;
    }

    .bpmn-animation-overlay {
        position: absolute;
        border-radius: var(--bpmn-animation-overlay-border-radius);
        border: var(--bpmn-animation-overlay-border);
        box-sizing: border-box;
        animation: pulse-glow 1.5s infinite;
    }
`;