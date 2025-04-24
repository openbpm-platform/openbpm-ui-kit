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

export interface SetElementColorCmd {
    elementId: string;
    fill: string;
    stroke: string;
}

export interface AddMarkerCmd {
    elementId: string;
    marker: string;
}

export interface RemoveMarkerCmd {
    elementId: string;
    marker: string;
}

export interface IncidentOverlayData {
    elementId: string;
    incidentCount: number;
    tooltipMessage: string;
}

export interface DecisionInstanceLinkOverlayData {
    activityId?: string;
    decisionInstanceId?: string;
    tooltipMessage?: string;
}