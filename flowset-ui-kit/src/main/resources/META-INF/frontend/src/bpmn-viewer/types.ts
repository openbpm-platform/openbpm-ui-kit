/*
 * Copyright (c) Haulmont 2025. All Rights Reserved.
 * Use is subject to license terms.
 */

export interface ProcessElement {
    $type: string;
    id?: string;
    name?: string;
}

export interface BpmProcessDefinition {
    key?: string;
    name?: string;
}

export interface CallActivityData {
    elementId: string;
    elementName?: string;
    calledElement: string;
    version?: string;
    versionTag?: string;
    binding?: string;
}

export interface BusinessRuleTaskData {
    elementId: string;
    elementName?: string;
    decisionRef: string;
    version?: string;
    versionTag?: string;
    binding?: string;
}

export interface SetElementColorCmd {
    elementId: string;
    fill: string;
    stroke: string;
}

/**
 * Marker ids applied as CSS classes to the diagram elements;
 * mirrors the server-side ElementMarkerType enum.
 */
export enum ElementMarkerType {
    RUNNING_ACTIVITY = "running-activity",
    FINISHED_ACTIVITY = "finished-activity",
    PASSED_FLOW = "passed-flow",
    MODIFICATION_SOURCE_ACTIVITY = "modification-source-activity",
    MODIFICATION_TARGET_ACTIVITY = "modification-target-activity",
    PRIMARY_COLOR_ACTIVITY = "primary-color-activity",
}

/**
 * Data to highlight the passed sequence flows; mirrors the server-side PassedFlowsCmdData.
 */
export interface PassedFlowsCmd {
    /** ids of the finished activity passes, ordered by start time, one entry per pass */
    finishedActivities?: string[];
    /** ids of the currently running activities, one entry per active token */
    runningActivities?: string[];
}

export interface AddMarkerCmd {
    elementId: string;
    marker: ElementMarkerType;
}

export interface RemoveMarkerCmd {
    elementId: string;
    marker: ElementMarkerType;
}

export interface SetInteractiveModeCmd {
    activeElements?: string[];
    disabledElements?: string[];
    /** marker whose coloring is previewed on hover, as the "<markerId>-hover" class */
    activeElementMarker?: ElementMarkerType;
    /** keep the "not passed as disabled" presentation active during this interactive session */
    notPassedAsDisabled?: boolean;
}

export enum AutoZoomDirection {
    CENTER = 'CENTER',
    LEFT = 'LEFT'
}

export interface ScrollToElementCmd {
    elementId: string;
    useAnimation: boolean;
    durationInSec: number;
    autoZoom?: boolean;
    autoZoomDirection?: AutoZoomDirection;
}

export enum ViewerMode {
    ReadOnly = 'READ_ONLY',
    Interactive = 'INTERACTIVE'
}

export interface ActivityData {
    id: string;
    name?: string;
    type: string;
}

export interface ElementTransactionBoundary {
    asyncBefore: boolean;
    asyncAfter: boolean;
    engineWaitState: boolean;
}

export enum BeforeElementTransactionType {
    ENGINE_WAIT_STATE = 'ENGINE_WAIT_STATE',
    ASYNC_BEFORE = 'ASYNC_BEFORE'
}
