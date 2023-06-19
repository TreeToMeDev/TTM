import { Component} from '@angular/core';
import { MatTreeNestedDataSource } from '@angular/material/tree';
import { NestedTreeControl } from '@angular/cdk/tree';

import { Agent } from '../../../models/agent';
import { AgentService } from '../../../services/agent.service';

interface AgentNode {
  name: string;
  children?: AgentNode[];
  id: number;
}

@Component({
  selector: 'agent-list-component',
  templateUrl: 'agent-list.component.html',
  styleUrls: ['agent-list.component.css'],
})

export class AgentListComponent {
  
  treeControl = new NestedTreeControl<AgentNode>(node => node.children);
  dataSource = new MatTreeNestedDataSource<AgentNode>();

  constructor(private agentService: AgentService) {
    this.agentService.fetchAll().subscribe(data => {
      let data2 : AgentNode[];
      data2 = this.agentService.buildTree(data, 0, 0);
      this.dataSource.data = data2;
    })
  }

  hasChild = (_: number, node: AgentNode) => !!node.children && node.children.length > 0;

}