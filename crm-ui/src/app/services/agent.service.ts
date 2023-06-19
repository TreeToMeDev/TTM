import { HttpClient } from "@angular/common/http";
import { Injectable } from '@angular/core';
import { Observable } from "rxjs";

import { ActionResponse } from '../models/action-response';
import { Agent } from "../models/agent";
import { environment } from '../../environments/environment';

interface AgentNode {
  name: string;
  children?: AgentNode[];
  id: number;
}

@Injectable({
  providedIn: 'root'
})

export class AgentService {

  url: string = environment.apiUrl + 'rest/agents';
  
  constructor(private http: HttpClient) { }
  
  fetch(id: number) {
    return this.http.get<Agent>(this.url + '/' + id);
  }

  fetchAll() : Observable<Agent[]> {
    return this.http.get<Agent[]>(this.url);
  }

  save(agent: Agent, id: number): Observable<ActionResponse> {
    if(isNaN(id) || id == -1) {
      return this.add(agent)
    } else {
      return this.change(agent, id)
    }
  }

  add(agent: Agent): Observable<ActionResponse> {
    return this.http.post<ActionResponse>(this.url, agent);
  }

  change(agent: Agent, id: number): Observable<ActionResponse> {
    return this.http.patch<ActionResponse>(this.url + "/" + id, agent);
  }

  delete(id: number) : Observable<Boolean> {
  	return this.http.delete<Boolean>(this.url + '/' + id);
  }

  // VERY Important! this matrix logic will FAIL in two cases:
  // 1. agent.id = agent.parent_id
  // 2. the parent_id at the top of the tree is -1 (it should be 0, -1 is for non-agent users)
  // Both of these conditions should be considered illegal and prevented via edits. Consider some tool to
  // look for these problems separately and/or add constraints to the DB to prevent this.

  // turn server data into a tree for nice presentation
  // the position in the hierarchy is indicated by the resultant JSON structure
  // TODO: consider moving this logic to server
  // TODO: alphabetize content of all nodes
  buildTree(agents: Agent[], level: number, parentId: number) : AgentNode[] {
    let ret : AgentNode[];
    ret = [];
    agents.forEach(agent =>{
      if(agent.level == level) {
        if(agent.parentId == parentId) {
          let d : AgentNode;
          d = {
            name: agent.firstName + ' ' + agent.lastName,
            children: this.buildTree(agents, level + 1, agent.id),
            id: agent.id
          }
          ret.push(d);
        }
      }
    })
    return ret;
  }

  // turn server data into a list suitable for drop-downs that indicate the structure of the hierarchy
  // the chevron indicates the position of each agent in the hierarchy
  // note resulting Agent records are incomplete, only include fields needed for dropdown
  // TODO: this looks "okay" but not great ....
  buildList(agents: Agent[], level: number, parentId: number) : Agent[] {
    let ret : Agent[];
    ret = [];
    agents.forEach(agent => {
      if(agent.level == level) {
        if(agent.parentId == parentId) {
          //let d : Agent as {};
          let chevron = ">".repeat(level + 1);
          let d: Agent = {
            firstName: chevron + " " + agent.firstName,
            lastName: agent.lastName,
            id: agent.id
          } as Agent;
          ret.push(d);
          let subList = this.buildList(agents, level + 1, agent.id);
          ret = ret.concat(subList);
        }
      }
    })
    return ret;
  }

}
