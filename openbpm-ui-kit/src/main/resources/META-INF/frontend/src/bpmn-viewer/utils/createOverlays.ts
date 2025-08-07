/*
 * Copyright (c) Haulmont 2025. All Rights Reserved.
 * Use is subject to license terms.
 */


import {OverlayPosition} from "../types";
import {getBBox} from 'diagram-js/lib/util/Elements';

export const createNavigationOverlay = (title?: string, handleClick?: () => void) => {
    const htmlDiv = document.createElement('div');
    htmlDiv.innerHTML = `
                <div class="navigation-overlay" title="${title}">
                    <svg width="16" height="16" viewBox="0 0 16 16" fill="none" xmlns="http://www.w3.org/2000/svg">
                        <path d="M16 7.9L10 3V6C9.5 6 8.9 6 8 6C0 6 0 14 0 14C0 14 1 10 7.8 10C8.9 10 9.6 10 10 10V12.9L16 7.9Z" fill="#ffffff"/>
                    </svg>
                </div>
                `;
    if (handleClick) {
        htmlDiv.addEventListener('click', (event: MouseEvent) => {
            handleClick();
        });
    }
    return {
        html: htmlDiv,
        // right bottom corner
        position: {
            right: 10,
            bottom: 15
        }
    };
}

export const createDocumentationOverlay = (element: any, title?: string, handleClick?: () => void) => {
    const htmlDiv = document.createElement('div');
    htmlDiv.innerHTML = `
            <div class="documentation-overlay" title="${title}">
                <svg width="16" height="16" viewBox="0 0 16 16" fill="#ff0000" xmlns="http://www.w3.org/2000/svg">

                    <g clip-path="url(#clip0_2388_139)">
                    <path d="M8 0C3.6 0 0 3.6 0 8C0 12.4 3.6 16 8 16C12.4 16 16 12.4 16 8C16 3.6 12.4 0 8 0ZM8.9 13H6.9V11H8.9V13ZM11 8.1C10.6 8.5 10.2 8.7 9.8 8.8C9.2 9.2 9 9 9 10H7C7 8 8.2 7.4 9 7C9.3 6.9 9.5 6.8 9.7 6.6C9.8 6.5 10 6.3 9.8 5.9C9.6 5.4 9 4.9 8.1 4.9C6.7 4.9 6.5 6.1 6.4 6.4L4.4 6.1C4.5 5 5.4 2.9 8 2.9C9.6 2.9 11 3.8 11.6 5.1C12 6.2 11.8 7.3 11 8.1Z" fill="#ffffff"/>
                    </g>
                    <defs>
                    <clipPath id="clip0_2388_139">
                    <rect width="16" height="16" fill="white"/>
                    </clipPath>
                    </defs>


                </svg>
            </div>
            `;

    if (handleClick) {
        htmlDiv.addEventListener('click', (event: MouseEvent) => {
            handleClick();
        });
    }


    let position: OverlayPosition = {right: 10, top: -10};  // top right corner

    if (element.type === 'bpmn:SequenceFlow') {
        let from = element.waypoints.length / 2 - 1;
        let to = from + 1;
        let minX = Math.min(element.waypoints[from].x, element.waypoints[to].x);
        let maxX = Math.max(element.waypoints[from].x, element.waypoints[to].x);
        let minY = Math.min(element.waypoints[from].y, element.waypoints[to].y);
        let maxY = Math.max(element.waypoints[from].y, element.waypoints[to].y);

        let waypointsBox = getBBox(element);

        position = {
            left: (minX - waypointsBox.x + (maxX - minX) * 0.5 - 8),
            top: (minY - waypointsBox.y + (maxY - minY) * 0.5 - 8)
        };
    }

    return {
        html: htmlDiv,
        position: position
    }
}